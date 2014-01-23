package de.tuhh.sts.team11.util;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public abstract class MessageReceiver extends CyclicBehaviour {
    private final Agent agent;
    private final MessageTemplate template;

    public MessageReceiver(Agent agent, MessageTemplate template) {
        this.agent = agent;
        this.template = template;
    }

    @Override
    public void action() {
        ACLMessage message = agent.receive(template);
        if (message == null) {
            block();
        } else {
            handleMessage(message);
        }
    }

    protected abstract void handleMessage(ACLMessage message);
}
