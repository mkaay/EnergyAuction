package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class PriceChangedEvent implements Serializable {
    private final int price;

    public PriceChangedEvent(final int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
