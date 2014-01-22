package de.tuhh.sts.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.database.PerstDatabase;
import de.tuhh.sts.team11.protocol.AuctionData;
import de.tuhh.sts.team11.protocol.LoginData;
import de.tuhh.sts.team11.util.Logger;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.states.MsgReceiver;


public class UserAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private static final MessageTemplate LOGIN_MESSAGE_TEMPLATE = MessageTemplate.and(MessageTemplate.MatchOntology
            ("login"), MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
            MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)));
    private static final MessageTemplate CREATE_MESSAGE_TEMPLATE = MessageTemplate.and(MessageTemplate.MatchOntology
            ("auctioncreate"), MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
            MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL)));

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
    }

    public void login(LoginData loginData) {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("login");
            msg.setContentObject(loginData);
            msg.addReceiver(marketplace);

            addBehaviour(new LoginHandler(this));
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createAuction(AuctionData auctionData) {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setOntology("auctioncreate");
            msg.setContentObject(auctionData);
            msg.addReceiver(marketplace);

            addBehaviour(new CreateHandler(this));
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class LoginHandler extends MsgReceiver {
        Agent agent;

        public LoginHandler(Agent agent) {
            super(agent, LOGIN_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
            this.agent = agent;
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }

            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                try {
                    userGUI.loginSuccess((PerstDatabase.UserData) msg.getContentObject());
                } catch (UnreadableException e) {
                    LOG.severe("UserData corrupted", e);
                }
            } else {
                userGUI.loginFailed();
            }

            agent.removeBehaviour(this);
        }
    }

    private class CreateHandler extends MsgReceiver {
        Agent agent;

        public CreateHandler(Agent agent) {
            super(agent, CREATE_MESSAGE_TEMPLATE, MsgReceiver.INFINITE, null, null);
            this.agent = agent;
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("got null message");
                return;
            }

            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                LOG.info("Success");
            } else {
                userGUI.loginFailed();
            }

            agent.removeBehaviour(this);
        }
    }
}
