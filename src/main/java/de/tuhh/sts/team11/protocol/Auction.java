package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.util.Types;

import java.io.Serializable;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/24/14
 */
public class Auction implements Serializable {
    private String name;
    private int amount;
    private Types.AuctionType type;
    private Date startTime;
    private Date endTime;
    private int price;
    private int priceDelta;
    private int timeDelta;
    private final Integer oid;

    public Auction(final String name, final Integer amount, final Integer price, final Types.AuctionType
            type, final Date startTime, final Date endTime,
                   final Integer priceDelta,
                   final Integer timeDelta, final Integer oid) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Types.AuctionType getType() {
        return type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    public int getPriceDelta() {
        return priceDelta;
    }

    public int getTimeDelta() {
        return timeDelta;
    }

    public Integer getOid() {
        return oid;
    }
}
