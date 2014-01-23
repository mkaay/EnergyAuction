package de.tuhh.sts.team11.protocol;

import de.tuhh.sts.team11.server.database.AuctionData;
import de.tuhh.sts.team11.util.Types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class ListAuctionsReply implements Serializable {
    private ArrayList<Auction> auctions = new ArrayList<Auction>();

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void addAuction(final AuctionData auction) {
        auctions.add(new Auction(auction.getName(), auction.getAmount(), auction.getPrice(),
                auction.getType(), auction.getStartTime(),
                auction.getEndTime(), auction.getPriceDelta(), auction.getTimeDelta()));
    }

    public class Auction implements Serializable {
        private String name;
        private int amount;
        private Types.AuctionType type;
        private Date startTime;
        private Date endTime;
        private int price;
        private int priceDelta;
        private int timeDelta;

        public Auction(final String name, final Integer amount, final Integer price, final Types.AuctionType
                type, final Date startTime, final Date endTime,
                       final Integer priceDelta,
                       final Integer timeDelta) {
            this.name = name;
            this.amount = amount;
            this.price = price;
            this.type = type;
            this.startTime = startTime;
            this.endTime = endTime;
            this.priceDelta = priceDelta;
            this.timeDelta = timeDelta;
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
    }
}
