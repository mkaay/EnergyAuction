package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.util.Types;

import java.io.Serializable;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class CreateAuctionOperation implements Serializable {
    private final String name;
    private final Integer amount;
    private final Integer price;
    private final Types.AuctionType auctionType;
    private final Types.AuctionDirection auctionDirection;
    private final Date endTime;
    private final Integer priceDelta;
    private final Integer timeDelta;

    public CreateAuctionOperation(final String name, final Integer amount, final Integer price,
                                  final Types.AuctionType auctionType,
                                  final Types.AuctionDirection auctionDirection, final Date endTime,
                                  final Integer priceDelta, final Integer timeDelta) {

        this.name = name;
        this.amount = amount;
        this.price = price;
        this.auctionType = auctionType;
        this.auctionDirection = auctionDirection;
        this.endTime = endTime;
        this.priceDelta = priceDelta;
        this.timeDelta = timeDelta;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getPrice() {
        return price;
    }

    public Types.AuctionType getAuctionType() {
        return auctionType;
    }

    public Types.AuctionDirection getAuctionDirection() {
        return auctionDirection;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getPriceDelta() {
        return priceDelta;
    }

    public Integer getTimeDelta() {
        return timeDelta;
    }
}
