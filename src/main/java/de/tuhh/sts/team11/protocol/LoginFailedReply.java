package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class LoginFailedReply implements Serializable {
    private final String username;

    public LoginFailedReply(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
