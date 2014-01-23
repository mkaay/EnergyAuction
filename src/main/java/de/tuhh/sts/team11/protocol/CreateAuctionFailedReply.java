package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class CreateAuctionFailedReply implements Serializable {
    private final String reason;

    public CreateAuctionFailedReply(final String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
