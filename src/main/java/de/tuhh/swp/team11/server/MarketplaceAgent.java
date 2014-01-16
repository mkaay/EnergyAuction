package de.tuhh.swp.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.AID;
import jade.core.Agent;

public class MarketplaceAgent extends Agent {
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! MarketplaceAgent "+getAID().getName()+" is ready.");
    }
}
