package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.BidData;
import de.tuhh.sts.team11.server.database.PerstDatabase;

import java.util.List;


/**
 * Created by mkaay on 14.01.14.
 */
public class ReverseDutchAuctionBehaviour extends AuctionBehaviour {
    public ReverseDutchAuctionBehaviour(final AuctionAgent agent, final AuctionData auctionData) {
        super(agent, auctionData);
    }

    @Override
    protected void changePrice() {
        AuctionData auctionData = getAuctionData();
        auctionData.setPrice(auctionData.getPrice() + auctionData.getPriceDelta());
        PerstDatabase.INSTANCE().getDB().beginTransaction();
        auctionData.store();
        PerstDatabase.INSTANCE().getDB().commitTransaction();
    }

    @Override
    protected boolean evaluteBids() {
        final List<BidData> bids = getAgent().getNewBids();
        if (bids.size() == 0) {
            return false;
        } else {
            getAuctionData().setAmount(0);
            PerstDatabase.INSTANCE().getDB().beginTransaction();
            getAuctionData().store();
            PerstDatabase.INSTANCE().getDB().commitTransaction();
            getAgent().setWon(bids.get(0));
            getAgent().setEvaluated(bids.get(0));
            return true;
        }
    }
}
