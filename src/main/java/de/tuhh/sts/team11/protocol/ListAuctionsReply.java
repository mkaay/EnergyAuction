package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.server.database.AuctionData;

import java.io.Serializable;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class ListAuctionsReply implements Serializable {
    private final List<AuctionData> auctions;

    public ListAuctionsReply(final List<AuctionData> auctions) {
        this.auctions = auctions;
    }

    public List<AuctionData> getAuctions() {
        return auctions;
    }
}
