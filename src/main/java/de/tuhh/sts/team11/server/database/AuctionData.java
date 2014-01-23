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

    public AuctionData(String name, int amount, Types.AuctionType auctionType, Date start, Date end, int price,
                       int priceDelta,
                       int timeDelta, Types.AuctionDirection auctionDirection) {
        this.name = name;
        this.amount = amount;
        this.auctionType = auctionType;
        this.startTime = start;
        this.endTime = end;
        this.price = price;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
        this.auctionDirection = auctionDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    Types.AuctionType getAuctionType() {
        return auctionType;
    }

    void setAuctionType(Types.AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    Date getStartTime() {
        return startTime;
    }

    void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    Date getEndTime() {
        return endTime;
    }

    void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    int getPrice() {
        return price;
    }

    void setPrice(int price) {
        this.price = price;
    }

    int getPriceDelta() {
        return priceDelta;
    }

    void setPriceDelta(int priceDelta) {
        this.priceDelta = priceDelta;
    }

    int getTimeDelta() {
        return timeDelta;
    }

    void setTimeDelta(int timeDelta) {
        this.timeDelta = timeDelta;
    }

    public Types.AuctionDirection getAuctionDirection() {
        return auctionDirection;
    }

    void setAuctionDirection(Types.AuctionDirection auctionDirection) {
        this.auctionDirection = auctionDirection;
    }
}