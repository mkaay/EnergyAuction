package de.tuhh.sts.team11.protocol;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class SubscribeOperation {
    private final String username;

    public SubscribeOperation(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
