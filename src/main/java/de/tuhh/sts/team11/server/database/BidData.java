package de.tuhh.sts.team11.server.database;

import org.garret.perst.Persistent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class BidData extends Persistent {
    int preis;
    long zeit;

    public BidData(int preis, long zeit) {
        this.preis = preis;
        this.zeit = zeit;
    }

    int getPreis() {
        return preis;
    }

    void setPreis(int preis) {
        this.preis = preis;
    }

    long getZeit() {
        return zeit;
    }

    void setZeit(long zeit) {
        this.zeit = zeit;
    }
}
