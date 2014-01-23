package de.tuhh.sts.team11.server;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.server.database.BidData;
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
        auctionData.setPrice(auctionData.getPrice() - auctionData.getPriceDelta());
        auctionData.store();
    }

    @Override
    protected boolean evaluteBids() {
        final List<BidData> bids = getAgent().getNewBids();
        if (bids.size() == 0) {
            return false;
        } else if (bids.size() == 1) {
            BidData bidData = bids.get(0);
            if (bidData.getAmount() == getAuctionData().getAmount()) {
                getAuctionData().addWinner(bidData);
                getAuctionData().setAmount(0);
                getAuctionData().store();
                getAgent().notifyWinner(bidData);
                return true;
            } else if (bidData.getAmount() < getAuctionData().getAmount()) {
                getAuctionData().addWinner(bidData);
                getAuctionData().setAmount(getAuctionData().getAmount() - bidData.getAmount());
                getAuctionData().store();
                getAgent().notifyWinner(bidData);
            } else {
                LOG.info("Amount too big");
            }

            return false;
        } else {
            for (BidData bidData : bids) {
                if (bidData.getAmount() == getAuctionData().getAmount()) {
                    getAuctionData().addWinner(bidData);
                    getAgent().notifyWinner(bidData);
                    return true;
                } else if (bidData.getAmount() < getAuctionData().getAmount()) {
                    getAuctionData().addWinner(bidData);
                    getAuctionData().setAmount(getAuctionData().getAmount() - bidData.getAmount());
                    getAgent().notifyWinner(bidData);
                } else {
                    LOG.info("Amount too big");
                }
            }

            getAuctionData().store();
            return false;
        }
    }
}
