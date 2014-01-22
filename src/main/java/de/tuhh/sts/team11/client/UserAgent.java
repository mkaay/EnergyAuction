package de.tuhh.sts.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.client.gui.UserGUI;
import de.tuhh.sts.team11.protocol.AuctionData;
import de.tuhh.sts.team11.protocol.LoginFailedReply;
import de.tuhh.sts.team11.protocol.LoginOperation;
import de.tuhh.sts.team11.protocol.LoginSuccessReply;
import de.tuhh.sts.team11.protocol.NewAccountOperation;
import de.tuhh.sts.team11.protocol.NewAuctionOperation;
import de.tuhh.sts.team11.util.Logger;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.states.MsgReceiver;

import java.util.Date;
import java.util.GregorianCalendar;


public class UserAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private static final MessageTemplate ACCOUNT_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("account");
    private static final MessageTemplate AUCTION_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("auction");

    private UserGUI userGUI;

    private final AID marketplace = new AID("marketplace", AID.ISLOCALNAME);

    protected void setup() {
        LOG.info("Registering UserAgent (" + getAID().getName() + ")");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("energy-useragent");
        sd.setName(getAID().getName());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        userGUI = new UserGUI(this);

        addBehaviour(new AccountHandler(this));
        addBehaviour(new AuctionHandler(this));
    }

    public void login(String username, String password) {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("account");
            msg.setContentObject(new LoginOperation(username, password));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createNewAccount(final String email, final String username, final String password) {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("account");
            msg.setContentObject(new NewAccountOperation(email, username, password));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createAuction(final String name, final Integer amount, final Integer price, final String type, final
    String direction, final Date endTime, final Integer priceDelta, final Integer timeDelta) {
        final AuctionData.Type auctionType = type == "dutch" ? AuctionData.Type.DUTCH : AuctionData.Type.REVERSE_DUTCH;
        final Date startTime = GregorianCalendar.getInstance().getTime();
        final AuctionData.Direction auctionDirection =
                direction == "buy" ? AuctionData.Direction.BUY : AuctionData.Direction.SELL;

        AuctionData auctionData = new AuctionData(name, amount, auctionType, startTime, endTime, price, priceDelta,
                timeDelta, auctionDirection);

        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("auction");
            msg.setContentObject(new NewAuctionOperation(auctionData));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class AccountHandler extends MsgReceiver {
        Agent agent;

        public AccountHandler(Agent agent) {
            super(agent, ACCOUNT_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
            this.agent = agent;
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }

            try {
                final Object content = msg.getContentObject();
                if (content instanceof LoginSuccessReply && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    //TODO
                } else if (content instanceof LoginFailedReply && msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    final LoginFailedReply loginFailedReply = (LoginFailedReply) content;

                    userGUI.loginFailed(loginFailedReply.getUsername());
                }
            } catch (UnreadableException e) {
                LOG.severe("message corrupted", e);
            }
        }
    }

    private class AuctionHandler extends MsgReceiver {
        Agent agent;

        public AuctionHandler(Agent agent) {
            super(agent, AUCTION_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
            this.agent = agent;
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }

            try {
                final Object content = msg.getContentObject();

                if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    //TODO
                } else if (msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    //TODO
                }
            } catch (UnreadableException e) {
                LOG.severe("message corrupted", e);
            }
        }
    }
}
