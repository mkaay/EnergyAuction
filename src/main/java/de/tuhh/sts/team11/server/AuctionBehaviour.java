package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.util.Logger;
import jade.core.behaviours.TickerBehaviour;


/**
 * Created by mkaay on 14.01.14.
 */
public abstract class AuctionBehaviour extends TickerBehaviour {
    private static final Logger LOG = Logger.getLogger(AuctionBehaviour.class.getName());
    private final AuctionAgent agent;
    private final AuctionData auctionData;

    public AuctionBehaviour(final AuctionAgent agent, final AuctionData auctionData) {
        super(agent, auctionData.getTimeDelta() * 1000);
        this.agent = agent;
        this.auctionData = auctionData;
    }

    public AuctionAgent getAgent() {
        return agent;
    }

    @Override
    protected void onTick() {
        LOG.info(String.format("Evaluating bids for %s", auctionData.getName()));
        if (!evaluteBids()) {
            changePrice();
            agent.priceChanged();
            LOG.info(String.format("%s changed price to %d", auctionData.getName(), auctionData.getPrice()));
        }
    }

    public AuctionData getAuctionData() {
        return auctionData;
    }

    protected abstract void changePrice();

    protected abstract boolean evaluteBids();
}
