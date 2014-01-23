package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.BidData;

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
        auctionData.store();
    }

    @Override
    protected boolean evaluteBids() {
        final List<BidData> bids = getAgent().getNewBids();
        if (bids.size() == 0) {
            return false;
        } else {
            getAuctionData().addWinner(bids.get(0));
            return true;
        }
    }
}
