package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class BidOperation implements Serializable {
    private final String bidder;
    private int amount;

    public BidOperation(final String bidder, final int amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public String getBidder() {
        return bidder;
    }

    public int getAmount() {
        return amount;
    }
}
