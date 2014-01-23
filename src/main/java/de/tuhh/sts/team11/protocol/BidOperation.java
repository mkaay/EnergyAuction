package de.tuhh.sts.team11.protocol;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class BidOperation {
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
