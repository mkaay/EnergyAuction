package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.server.database.AuctionData;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class CreateAuctionOperation implements Serializable {
    private final AuctionData auctionData;

    public CreateAuctionOperation(final AuctionData auctionData) {

        this.auctionData = auctionData;
    }

    public AuctionData getAuctionData() {
        return auctionData;
    }
}
