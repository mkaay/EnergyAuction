package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.BidData;
import de.tuhh.sts.team11.server.database.PerstDatabase;
import de.tuhh.sts.team11.util.Logger;

import java.util.List;


/**
 * Created by mkaay on 14.01.14.
 */
public class DutchAuctionBehaviour extends AuctionBehaviour {
    private static final Logger LOG = Logger.getLogger(DutchAuctionBehaviour.class.getName());

    public DutchAuctionBehaviour(final AuctionAgent agent, final AuctionData auctionData) {
        super(agent, auctionData);
    }

    @Override
    protected void changePrice() {
        AuctionData auctionData = getAuctionData();
        final int newPrice = auctionData.getPrice() - auctionData.getPriceDelta();
        if (newPrice >= 1) {
            auctionData.setPrice(newPrice);
            PerstDatabase.INSTANCE().getDB().beginTransaction();
            auctionData.store();
            PerstDatabase.INSTANCE().getDB().commitTransaction();
        }
    }

    @Override
    protected boolean evaluteBids() {
        final List<BidData> bids = getAgent().getNewBids();
        if (bids.size() == 0) {
            return false;
        } else if (bids.size() == 1) {
            BidData bidData = bids.get(0);
            if (bidData.getAmount() == getAuctionData().getAmount()) {
                PerstDatabase.INSTANCE().getDB().beginTransaction();
                getAuctionData().setAmount(0);
                getAuctionData().store();
                PerstDatabase.INSTANCE().getDB().commitTransaction();
                getAgent().setWon(bidData);
                return true;
            } else if (bidData.getAmount() < getAuctionData().getAmount()) {
                PerstDatabase.INSTANCE().getDB().beginTransaction();
                getAuctionData().setAmount(getAuctionData().getAmount() - bidData.getAmount());
                getAuctionData().store();
                PerstDatabase.INSTANCE().getDB().commitTransaction();
                getAgent().setWon(bidData);
            } else {
                LOG.info("Amount too big");
            }
            getAgent().setEvaluated(bidData);

            return false;
        } else {
            for (BidData bidData : bids) {
                if (bidData.getAmount() == getAuctionData().getAmount()) {
                    getAuctionData().setAmount(0);
                    getAgent().setWon(bidData);
                    return true;
                } else if (bidData.getAmount() < getAuctionData().getAmount()) {
                    getAuctionData().setAmount(getAuctionData().getAmount() - bidData.getAmount());
                    getAgent().setWon(bidData);
                } else {
                    LOG.info("Amount too big");
                }
                getAgent().setEvaluated(bidData);
            }

            PerstDatabase.INSTANCE().getDB().beginTransaction();
            getAuctionData().store();
            PerstDatabase.INSTANCE().getDB().commitTransaction();
            return false;
        }
    }
}
