package de.tuhh.sts.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.logging.Logger;


public class BidAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(BidAgent.class.getName());

    private final AID marketplace = new AID("marketplace", AID.ISLOCALNAME);

    protected void setup() {
        LOG.info("Registering BidAgent (" + getAID().getName() + ")");

        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setContentObject("test");
            msg.addReceiver(marketplace);
            System.out.println("Kontaktiere den Agenten... ");
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
