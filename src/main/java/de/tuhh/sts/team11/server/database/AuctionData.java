package de.tuhh.sts.team11.server.database;

import de.tuhh.sts.team11.util.Types;
import org.garret.perst.Link;
import org.garret.perst.Persistent;

import java.util.Date;


/**
 * Created by mkaay on 21.01.14.
 */
public class AuctionData extends Persistent {
    private String name;
    private int amount;
    private Types.AuctionType type;
    private Date startTime;
    private Date endTime;
    private int price;
    private int priceDelta;
    private int timeDelta;
    private Link<BidData> bids;
    private BidData winner;

    protected AuctionData(final String name, final Integer amount, final Integer price, final Types.AuctionType
            type, final Date startTime, final Date endTime, final Integer priceDelta, final Integer timeDelta) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
        bids = PerstDatabase.INSTANCE().getStorage().createLink();
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Types.AuctionType getType() {
        return type;
    }

    public void setType(Types.AuctionType type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceDelta() {
        return priceDelta;
    }

    public void setPriceDelta(int priceDelta) {
        this.priceDelta = priceDelta;
    }

    public int getTimeDelta() {
        return timeDelta;
    }

    public void setTimeDelta(int timeDelta) {
        this.timeDelta = timeDelta;
    }

    public void addWinner(final BidData winner) {
        this.winner = winner;
    }

    public BidData getWinner() {
        return winner;
    }

    protected void addBid(final BidData bidData) {
        bids.add(bidData);
    }
}