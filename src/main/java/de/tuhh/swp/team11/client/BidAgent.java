package de.tuhh.swp.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class BidAgent extends Agent {
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! BidAgent "+getAID().getName()+" is ready.");

        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        try {
            msg.setContentObject("test");
            msg.addReceiver(new AID("marktplatz", AID.ISLOCALNAME));
            System.out.println("Kontaktiere den Agenten... ");
            send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
