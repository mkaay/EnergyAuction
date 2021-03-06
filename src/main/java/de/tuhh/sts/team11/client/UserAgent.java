package de.tuhh.sts.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.client.gui.UserGUI;
import de.tuhh.sts.team11.protocol.*;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.MessageReceiver;
import de.tuhh.sts.team11.util.Types;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.Date;


public class UserAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private static final MessageTemplate ACCOUNT_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("account");
    private static final MessageTemplate AUCTION_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("auction");

    private UserGUI userGUI;

    private final AID marketplace = new AID("marketplace", AID.ISLOCALNAME);
    private String username;

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
        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                refreshAuctions();
            }
        });
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
            msg.setContentObject(new CreateAccountOperation(email, username, password));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createAuction(final String name, final Integer amount, final Integer price,
                              final Types.AuctionType type, final Date endTime, final Integer priceDelta,
                              final Integer timeDelta) {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("auction");
            msg.setContentObject(new CreateAuctionOperation(name, amount, price, type, endTime, priceDelta, timeDelta));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshAuctions() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        try {
            msg.setOntology("auction");
            msg.setContentObject(new ListAuctionsOperation());
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createBidAgent(final Auction auctionData, final Integer amount, final Integer price) {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        try {
            msg.setOntology("bid");
            msg.setContentObject(new CreateBidAgentOperation(auctionData, amount, price, getUsername()));
            msg.addReceiver(marketplace);
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    class AccountHandler extends MessageReceiver {
        public AccountHandler(Agent agent) {
            super(agent, ACCOUNT_MESSAGE_TEMPLATE);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            try {
                final Object content = msg.getContentObject();
                if (content instanceof LoginSuccessReply && msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    LoginSuccessReply reply = (LoginSuccessReply) content;
                    setUsername(reply.getUsername());
                    userGUI.loginSuccess();
                } else if (content instanceof LoginFailedReply && msg.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
                    final LoginFailedReply loginFailedReply = (LoginFailedReply) content;

                    userGUI.loginFailed(loginFailedReply.getUsername());
                }
            } catch (UnreadableException e) {
                LOG.severe("message corrupted", e);
            }
        }
    }

    private class AuctionHandler extends MessageReceiver {
        public AuctionHandler(Agent agent) {
            super(agent, AUCTION_MESSAGE_TEMPLATE);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            try {
                final Object content = msg.getContentObject();

                if (content instanceof ListAuctionsReply) {
                    LOG.info("Got auctions");
                    ListAuctionsReply reply = (ListAuctionsReply) content;
                    userGUI.setAuctionList(reply.getAuctions());
                } else if (content instanceof CreateAuctionSuccessReply && msg.getPerformative() == ACLMessage
                        .ACCEPT_PROPOSAL) {
                    userGUI.createAuctionSuccess();
                } else if (content instanceof CreateAuctionFailedReply && msg.getPerformative() == ACLMessage
                        .REJECT_PROPOSAL) {
                    userGUI.createAuctionFailed();
                }
            } catch (UnreadableException e) {
                LOG.severe("message corrupted", e);
            }
        }
    }
}
