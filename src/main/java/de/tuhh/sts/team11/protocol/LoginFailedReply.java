package de.tuhh.sts.team11.protocol;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class LoginFailedReply {
    private final String username;

    public LoginFailedReply(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
