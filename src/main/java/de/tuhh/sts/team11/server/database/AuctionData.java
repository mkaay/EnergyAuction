package de.tuhh.sts.team11.server.database;

import de.tuhh.sts.team11.util.Types;
import org.garret.perst.Persistent;

import java.io.Serializable;
import java.util.Date;

/**
* Created by mkaay on 21.01.14.
*/
public class AuctionData extends Persistent implements Serializable {

    private String name;
    private int amount;
    private Types.AuctionType auctionType;
    private Date startTime;
    private Date endTime;
    private int price;
    private int priceDelta;
    private int timeDelta;
    private Types.AuctionDirection auctionDirection;

    public AuctionData(final String name, final Integer amount, final Integer price, final Types.AuctionType
            auctionType, final Types.AuctionDirection auctionDirection, final Date startTime, final Date endTime,
                       final Integer priceDelta,
                       final Integer timeDelta) {
        this.name = name;
        this.amount = amount;
        this.auctionType = auctionType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
        this.auctionDirection = auctionDirection;
    }

    public AuctionData() {

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

    public Types.AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(Types.AuctionType auctionType) {
        this.auctionType = auctionType;
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

    public Types.AuctionDirection getAuctionDirection() {
        return auctionDirection;
    }

    public void setAuctionDirection(Types.AuctionDirection auctionDirection) {
        this.auctionDirection = auctionDirection;
    }
}