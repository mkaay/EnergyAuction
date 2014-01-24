package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.server.database.AuctionData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class ListAuctionsReply implements Serializable {
    private ArrayList<Auction> auctions = new ArrayList<Auction>();

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void addAuction(final AuctionData auction) {
        auctions.add(new Auction(auction.getName(), auction.getAmount(), auction.getPrice(),
                auction.getType(), auction.getStartTime(),
                auction.getEndTime(), auction.getPriceDelta(), auction.getTimeDelta(), auction.getOid()));
    }

}
