package de.tuhh.sts.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.protocol.LoginData;
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


public class MarketplaceAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(MarketplaceAgent.class.getName());

    private static final MessageTemplate LOGIN_MESSAGE_TEMPLATE = MessageTemplate.and(MessageTemplate.MatchOntology
            ("login"), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

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
                LoginData loginData = (LoginData) msg.getContentObject();
                LOG.info(String.format("Login for user %s", loginData.getUsername()));
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                send(reply);
            } catch (UnreadableException e) {
                LOG.warning("corrup LoginData", e);
            }
        }
    }
}
