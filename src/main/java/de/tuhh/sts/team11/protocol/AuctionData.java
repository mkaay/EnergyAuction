package de.tuhh.sts.team11.protocol;

import org.garret.perst.Persistent;

import java.io.Serializable;
import java.util.Date;

/**
* Created by mkaay on 21.01.14.
*/
public class AuctionData extends Persistent implements Serializable {
    public enum Type {DUTCH, REVERSE_DUTCH}

    ;

    public enum Direction {BUY, SELL}

    ;

    private String name;
    private int amount;
    private Type type;
    private Date startTime;
    private Date endTime;
    private int price;
    private int priceDelta;
    private int timeDelta;
    private Direction direction;

    public AuctionData(String name, int amount, Type type, Date start, Date end, int price, int priceDelta,
                       int timeDelta, Direction direction) {
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.startTime = start;
        this.endTime = end;
        this.price = price;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
        this.direction = direction;
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

    Type getType() {
        return type;
    }

    void setType(Type type) {
        this.type = type;
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

    public Direction getDirection() {
        return direction;
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }
}