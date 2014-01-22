package de.tuhh.sts.team11.client;

import de.tuhh.sts.team11.database.PerstDatabase;
import de.tuhh.sts.team11.protocol.AuctionData;
import de.tuhh.sts.team11.util.Logger;

import javax.swing.*;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class UserGUI {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private final UserAgent userAgent;
    private JFrame loginForm;
    private JFrame searchForm;

    public UserGUI(final UserAgent userAgent) {
        this.userAgent = userAgent;

        searchForm = SearchGUI.createForm(this);
    }

    public void loginSuccess(final PerstDatabase.UserData userData) {
        LOG.info("Login success");
    }

    public void loginFailed() {
        LOG.info("Login fail");
    }

    public void newAuction() {
        NewAuction.createForm(this);
    }

    public void createAuction(AuctionData auctionData) {
        userAgent.createAuction(auctionData);
    }
}
