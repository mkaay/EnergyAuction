package de.tuhh.sts.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.protocol.AuctionData;
import de.tuhh.sts.team11.protocol.LoginOperation;
import de.tuhh.sts.team11.protocol.LoginSuccessReply;
import de.tuhh.sts.team11.protocol.UserData;
import de.tuhh.sts.team11.util.Logger;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.states.MsgReceiver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MarketplaceAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(MarketplaceAgent.class.getName());

    private static final MessageTemplate LOGIN_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("account");
    private static final MessageTemplate AUCTION_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("auction");

    private final List<String> authorizedUsers;

    public MarketplaceAgent() {
        authorizedUsers = new LinkedList<String>();
    }

    protected void setup() {
        LOG.info("Registering Marketplace (" + getAID().getName() + ")");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("energy-marketplace");
        sd.setName("marketplace");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new LoginHandler(this));
        addBehaviour(new AuctionHandler(this));
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        LOG.info("Marketplace (" + getAID().getName() + ") terminating");
    }

    class LoginHandler extends MsgReceiver {
        public LoginHandler(MarketplaceAgent agent) {
            super(agent, LOGIN_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }


            try {
                Object content = msg.getContentObject();

                if (content instanceof LoginOperation && msg.getPerformative() == ACLMessage.PROPOSE) {
                    LoginOperation loginOperation = (LoginOperation) content;
                    LOG.info(String.format("Login for user %s", loginOperation.getUsername()));
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContentObject(new LoginSuccessReply(new UserData(loginOperation.getUsername(), "",
                            loginOperation.getPassword())));
                    send(reply);
                }
            } catch (UnreadableException e) {
                LOG.warning("corrupt message", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class AuctionHandler extends MsgReceiver {
        public AuctionHandler(MarketplaceAgent agent) {
            super(agent, AUCTION_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }

            try {
                AuctionData auctionData = (AuctionData) msg.getContentObject();
                LOG.info(String.format("New Auction %s", auctionData.getName()));
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                send(reply);
            } catch (UnreadableException e) {
                LOG.warning("corrupt AuctionData", e);
            }
        }
    }
}
