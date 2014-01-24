package de.tuhh.sts.team11.server.database;

import org.garret.perst.Persistent;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class BidData extends Persistent {
    private final int price;
    private final int amount;
    private final AuctionData auction;
    private final Date time;
    private final UserData user;
    private boolean evaluated;
    private boolean won;

    protected BidData(int price, int amount, AuctionData auction, Date time, UserData user) {
        this.price = price;
        this.amount = amount;
        this.auction = auction;
        this.time = time;
        this.user = user;
    }

    public int getPrice() {
        return price;
    }

    public Date getTime() {
        return time;
    }

    public AuctionData getAuction() {
        return auction;
    }

    public UserData getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(final boolean evaluated) {
        this.evaluated = evaluated;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(final boolean won) {
        this.won = won;
    }
}
