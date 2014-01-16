package de.tuhh.swp.team11.client;

/**
 * Created by mkaay on 14.01.14.
 */

import jade.core.Agent;

public class UserAgent extends Agent {
    protected void setup() {
        // Printout a welcome message
        System.out.println("Hello! UserAgent "+getAID().getName()+" is ready.");
    }
}
