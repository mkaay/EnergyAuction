package de.tuhh.sts.team11.client;

import de.tuhh.sts.team11.database.PerstDatabase;
import de.tuhh.sts.team11.util.Logger;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class UserGUI {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    public UserGUI() {

    }

    public void loginSuccess(final PerstDatabase.UserData userData) {
        LOG.info("Login success");
    }

    public void loginFailed() {
        LOG.info("Login fail");
    }
}
