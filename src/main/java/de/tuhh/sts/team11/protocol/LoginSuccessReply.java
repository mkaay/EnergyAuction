package de.tuhh.sts.team11.protocol;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class LoginSuccessReply implements Serializable {
    private final UserData userData;

    public LoginSuccessReply(final UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}
