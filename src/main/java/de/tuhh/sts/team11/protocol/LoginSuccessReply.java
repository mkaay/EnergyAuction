package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class LoginSuccessReply implements Serializable {
    private final String username;
    private final String email;

    public LoginSuccessReply(final String username, final String email) {

        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
