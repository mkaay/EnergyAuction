package de.tuhh.sts.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.protocol.AuctionWinnerEvent;
import de.tuhh.sts.team11.protocol.BidOperation;
import de.tuhh.sts.team11.protocol.PriceChangedEvent;
import de.tuhh.sts.team11.protocol.SubscribeOperation;
import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.BidData;
import de.tuhh.sts.team11.server.database.PerstDatabase;
import de.tuhh.sts.team11.server.database.UserData;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.MessageReceiver;
import de.tuhh.sts.team11.util.Types;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class AuctionAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(AuctionAgent.class.getName());
    private AuctionData auctionData;
    private final Map<UserData, AID> subscribed = new HashMap<UserData, AID>();
    private List<BidData> bids = new LinkedList<BidData>();

    private final AID marketplace = new AID("marketplace", AID.ISLOCALNAME);

    protected void setup() {
        Object[] args = getArguments();
        auctionData = (AuctionData) args[0];

        LOG.info(String.format("Registering AuctionAgent (%s) for auction '%s'", getAID().getName(),
                auctionData.getName()));

        if (auctionData.getType() == Types.AuctionType.DUTCH) {
            addBehaviour(new DutchAuctionBehaviour(this, auctionData));
        } else {
            addBehaviour(new ReverseDutchAuctionBehaviour(this, auctionData));
        }

        addBehaviour(new WakerBehaviour(this, auctionData.getEndTime()) {
            @Override
            protected void onWake() {
                endAuction();
            }
        });
        addBehaviour(new Receiver(this));

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("energy-auction");
        sd.setName(String.format("auction_id%s", auctionData.getOid()));
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    protected void endAuction() {
        doDelete();
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        LOG.info(String.format("AuctionAgent (%s) for auction '%s' terminating", getAID().getName(),
                auctionData.getName()));
    }

    public void priceChanged() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("auction");
        for (AID receiver : subscribed.values()) {
            message.addReceiver(receiver);
        }
        try {
            message.setContentObject(new PriceChangedEvent(auctionData.getPrice()));
            send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BidData> getNewBids() {
        List<BidData> bids = new ArrayList<BidData>(this.bids);
        this.bids.clear();
        return bids;
    }

    public void notifyWinner(final BidData bidData) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("auction");
        message.addReceiver(subscribed.get(bidData.getUser()));
        try {
            message.setContentObject(new AuctionWinnerEvent());
            send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Receiver extends MessageReceiver {
        public Receiver(final Agent agent) {
            super(agent, MessageTemplate.MatchOntology("auction"));
        }

        @Override
        protected void handleMessage(final ACLMessage message) {
            try {
                Object content = message.getContentObject();

                if (message.getPerformative() == ACLMessage.SUBSCRIBE) {
                    SubscribeOperation operation = (SubscribeOperation) content;
                    subscribed.put(PerstDatabase.INSTANCE().getUser(operation.getUsername()), message.getSender());

                    ACLMessage reply = message.createReply();
                    try {
                        reply.setContentObject(new PriceChangedEvent(auctionData.getPrice()));
                        send(reply);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (content instanceof BidOperation && message.getPerformative() == ACLMessage.PROPOSE) {
                    BidOperation bidOperation = (BidOperation) content;
                    LOG.info("got bid");
                    final UserData userData = PerstDatabase.INSTANCE().getUser(bidOperation.getBidder());
                    if (userData != null) {
                        BidData bidData = PerstDatabase.INSTANCE().createBid(bidOperation.getAmount(), auctionData,
                                userData);
                        bids.add(bidData);
                    } else {
                        LOG.info("invalid user");
                    }
                }
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        }
    }
}