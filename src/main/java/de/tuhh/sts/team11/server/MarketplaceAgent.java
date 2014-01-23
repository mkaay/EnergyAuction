package de.tuhh.sts.team11.server;

/**
 * Created by mkaay on 14.01.14.
 */

import de.tuhh.sts.team11.protocol.*;
import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.PerstDatabase;
import de.tuhh.sts.team11.server.database.UserData;
import de.tuhh.sts.team11.server.exceptions.UsernameAlreadyTakenException;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.MessageReceiver;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("UnusedDeclaration")
public class MarketplaceAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(MarketplaceAgent.class.getName());

    private static final MessageTemplate LOGIN_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("account");
    private static final MessageTemplate AUCTION_MESSAGE_TEMPLATE = MessageTemplate.MatchOntology("auction");

    private List<UserData> authorizedUsers;
    private Map<AID, UserData> aidToUser;

    protected void setup() {
        authorizedUsers = new LinkedList<UserData>();
        aidToUser = new HashMap<AID, UserData>();

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

        restore();

        addBehaviour(new AccountHandler(this));
        addBehaviour(new AuctionHandler(this));
    }

    private void restore() {
        for (AuctionData auctionData : PerstDatabase.INSTANCE().getAuctions()) {
            createAuctionAgent(auctionData);
        }
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        LOG.info("Marketplace (" + getAID().getName() + ") terminating");
    }

    class AccountHandler extends MessageReceiver {
        public AccountHandler(final Agent agent) {
            super(agent, LOGIN_MESSAGE_TEMPLATE);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            try {
                Object content = msg.getContentObject();

                if (content instanceof LoginOperation && msg.getPerformative() == ACLMessage.PROPOSE) {
                    LoginOperation operation = (LoginOperation) content;
                    LOG.info(String.format("Login for user %s", operation.getUsername()));
                    ACLMessage reply = msg.createReply();

                    UserData userData = PerstDatabase.INSTANCE().getUser(operation.getUsername());
                    if (userData != null && userData.getPassword().equals(operation.getPassword())) {
                        LOG.info("Login ok");
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setContentObject(new LoginSuccessReply(userData.getUsername(), userData.getEmail()));
                        userLogin(userData, msg.getSender());
                    } else {
                        LOG.info("Login failed");
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setContentObject(new LoginFailedReply(operation.getUsername()));
                    }

                    send(reply);
                } else if (content instanceof CreateAccountOperation && msg.getPerformative() == ACLMessage.PROPOSE) {
                    CreateAccountOperation operation = (CreateAccountOperation) content;

                    ACLMessage reply = msg.createReply();
                    LOG.info("Creating account");

                    try {
                        UserData userData = PerstDatabase.INSTANCE().createNewUser(operation.getEmail(),
                                operation.getUsername(), operation.getPassword());
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        LOG.info("Created, logging in");
                        // auto login
                        reply.setContentObject(new LoginSuccessReply(userData.getUsername(), userData.getEmail()));
                        userLogin(userData, msg.getSender());
                    } catch (UsernameAlreadyTakenException e) {
                        LOG.info("Username taken");
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setContentObject(new CreateAccountFailedReply("Username already taken!"));
                    }

                    send(reply);
                }
            } catch (UnreadableException e) {
                LOG.warning("corrupt message", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void userLogin(final UserData userData, final AID sender) {
        authorizedUsers.add(userData);
        aidToUser.put(sender, userData);
    }

    class AuctionHandler extends MessageReceiver {
        public AuctionHandler(final Agent agent) {
            super(agent, AUCTION_MESSAGE_TEMPLATE);
        }

        @Override
        protected void handleMessage(final ACLMessage msg) {
            try {
                Object content = msg.getContentObject();

                if (content instanceof CreateAuctionOperation) {
                    ACLMessage reply = msg.createReply();

                    if (aidToUser.containsKey(msg.getSender())) {
                        LOG.info("Creating new auction");

                        CreateAuctionOperation o = (CreateAuctionOperation) content;
                        PerstDatabase.INSTANCE().createAuction(o.getName(), o.getAmount(), o.getPrice(),
                                o.getAuctionType(), o.getEndTime(), o.getPriceDelta(),
                                o.getTimeDelta());

                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        reply.setContentObject(new CreateAuctionSuccessReply());

                        ACLMessage message = createAuctionListMessage();
                        for (AID aid : aidToUser.keySet()) {
                            message.addReceiver(aid);
                        }

                        send(message);
                    } else {
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        reply.setContentObject(new CreateAuctionFailedReply("not logged in"));
                        LOG.info("User not logged in");
                    }

                    send(reply);
                } else if (content instanceof ListAuctionsOperation) {
                    ACLMessage message = createAuctionListMessage();
                    message.addReceiver(msg.getSender());
                    send(message);
                }
            } catch (UnreadableException e) {
                LOG.warning("corrupt message", e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ACLMessage createAuctionListMessage() throws IOException {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setOntology("auction");

        final ListAuctionsReply listAuctionsReply = new ListAuctionsReply();
        for (AuctionData auctionData : PerstDatabase.INSTANCE().getAuctions()) {
            listAuctionsReply.addAuction(auctionData);
        }

        message.setContentObject(listAuctionsReply);

        return message;
    }

    private void createAuctionAgent(AuctionData auctionData) {
        Object[] args = {auctionData};

        AgentContainer agentContainer = getContainerController();
        try {
            AgentController controller = agentContainer.createNewAgent(String.format("auction_id%d",
                    auctionData.getOid()), "de.tuhh.sts.team11.server.AuctionAgent", args);
            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
