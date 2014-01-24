package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class SubscribeOperation implements Serializable {
    private final int oid;

    public SubscribeOperation(int oid) {

        this.oid = oid;
    }

    public int getOid() {
        return oid;
    }
}
