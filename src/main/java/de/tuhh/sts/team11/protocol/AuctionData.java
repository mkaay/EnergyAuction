package de.tuhh.sts.team11.protocol;

import java.io.Serializable;
import java.util.Date;

/**
* Created by mkaay on 21.01.14.
*/
public class AuctionData implements Serializable {
    String name;
    int energieMenge;
    String typ;
    Date start;
    Date ende;
    int preis;
    int preisDelta;
    int zeitDelta;
    boolean kaufen;

    public AuctionData(String name, int energieMenge, String typ, Date start, Date ende, int preis,
                       int preisDelta,
                       int zeitDelta, boolean kaufen) {
        this.name = name;
        this.energieMenge = energieMenge;
        this.typ = typ;
        this.start = start;
        this.ende = ende;
        this.preis = preis;
        this.preisDelta = preisDelta;
        this.zeitDelta = zeitDelta;
        this.kaufen = kaufen;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    int getEnergieMenge() {
        return energieMenge;
    }

    void setEnergieMenge(int energieMenge) {
        this.energieMenge = energieMenge;
    }

    String getTyp() {
        return typ;
    }

    void setTyp(String typ) {
        this.typ = typ;
    }

    Date getStart() {
        return start;
    }

    void setStart(Date start) {
        this.start = start;
    }

    Date getEnde() {
        return ende;
    }

    void setEnde(Date ende) {
        this.ende = ende;
    }

    int getPreis() {
        return preis;
    }

    void setPreis(int preis) {
        this.preis = preis;
    }

    int getPreisDelta() {
        return preisDelta;
    }

    void setPreisDelta(int preisDelta) {
        this.preisDelta = preisDelta;
    }

    int getZeitDelta() {
        return zeitDelta;
    }

    void setZeitDelta(int zeitDelta) {
        this.zeitDelta = zeitDelta;
    }

    boolean isKaufen() {
        return kaufen;
    }

    void setKaufen(boolean kaufen) {
        this.kaufen = kaufen;
    }

}
