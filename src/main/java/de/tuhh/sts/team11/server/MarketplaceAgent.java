package de.tuhh.sts.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

import java.util.logging.Logger;


public class MarketplaceAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(MarketplaceAgent.class.getName());

    protected void setup() {
        LOG.info("Registering Marketplace (" + getAID().getName() + ")");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("marketplace");
        sd.setName("energy-marketplace");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new Receiver(this));
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        LOG.info("Marketplace (" + getAID().getName() + ") terminating");
    }

    class Receiver extends MsgReceiver {
        public Receiver(MarketplaceAgent agent) {
            super(agent, MessageTemplate.MatchAll(), MsgReceiver.INFINITE, null, null);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            if (msg == null) {
                LOG.info("timeout");
            }

            LOG.info(msg.getContent());
        }
    }
}
