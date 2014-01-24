package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/24/14
 */
public class CreateBidAgentOperation implements Serializable {
    private final Auction auctionData;
    private final Integer amount;
    private final Integer price;
    private final String username;

    public CreateBidAgentOperation(final Auction auctionData, final Integer amount,
                                   final Integer price,
                                   final String username) {
        this.auctionData = auctionData;
        this.amount = amount;
        this.price = price;
        this.username = username;
    }

    public Auction getAuctionData() {
        return auctionData;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUsername() {
        return username;
    }
}
