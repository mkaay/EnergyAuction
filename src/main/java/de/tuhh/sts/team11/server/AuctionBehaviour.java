package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import jade.core.behaviours.TickerBehaviour;


/**
 * Created by mkaay on 14.01.14.
 */
public abstract class AuctionBehaviour extends TickerBehaviour {
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
        if (!evaluteBids()) {
            changePrice();
            agent.priceChanged();
        }
    }

    public AuctionData getAuctionData() {
        return auctionData;
    }

    protected abstract void changePrice();

    protected abstract boolean evaluteBids();
}
