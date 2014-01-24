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
import de.tuhh.sts.team11.server.database.UserData;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.MessageReceiver;
import de.tuhh.sts.team11.util.Types;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;


public class BidAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(BidAgent.class.getName());

    private AuctionData auctionData;
    private UserData userData;
    private Integer amount;
    private Integer price;
    private AID auctionAgent;
    private boolean priceAccepted;
    private BidData bidData;

    protected void setup() {
        Object[] args = getArguments();
        bidData = (BidData) args[0];
        auctionData = bidData.getAuction();
        userData = bidData.getUser();
        amount = bidData.getAmount();
        price = bidData.getPrice();

        auctionAgent = new AID(String.format("auction_id%d", auctionData.getOid()), AID.ISLOCALNAME);

        LOG.info("Registering BidAgent (" + getAID().getName() + ")");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("energy-auction");
        sd.setName(String.format("bidder_bid%d", bidData.getOid()));
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        ACLMessage message = new ACLMessage(ACLMessage.SUBSCRIBE);
        message.addReceiver(auctionAgent);
        message.setOntology("auction");
        try {
            message.setContentObject(new SubscribeOperation(bidData.getOid()));
            send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addBehaviour(new MessageReceiver(this, MessageTemplate.MatchOntology("auction")) {
            @Override
            protected void handleMessage(final ACLMessage message) {
                try {
                    Object content = message.getContentObject();

                    if (content instanceof PriceChangedEvent && !priceAccepted) {
                        PriceChangedEvent event = (PriceChangedEvent) content;

                        priceChanged(event.getPrice());
                    } else if (content instanceof AuctionWinnerEvent) {
                        doDelete();
                    }
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void priceChanged(final int newPrice) {
        if (auctionData.getType() == Types.AuctionType.REVERSE_DUTCH) {
            if (newPrice >= price) {
                acceptPrice();
            }
        } else {
            if (newPrice <= price) {
                acceptPrice();
            }
        }
    }

    private void acceptPrice() {
        priceAccepted = true;
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(auctionAgent);
        message.setOntology("auction");
        try {
            message.setContentObject(new BidOperation(bidData.getOid()));
            send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        LOG.info("BidAgent (" + getAID().getName() + ") terminating");
    }
}
