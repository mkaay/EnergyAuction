package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class NewAuctionOperation implements Serializable {
    private final AuctionData auctionData;

    public NewAuctionOperation(final AuctionData auctionData) {

        this.auctionData = auctionData;
    }

    public AuctionData getAuctionData() {
        return auctionData;
    }
}
