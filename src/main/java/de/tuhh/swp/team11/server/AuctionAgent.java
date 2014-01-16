package de.tuhh.swp.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.Agent;

public class AuctionAgent extends Agent {
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! AuctionAgent "+getAID().getName()+" is ready.");
    }
}
