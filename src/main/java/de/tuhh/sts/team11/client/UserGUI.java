package de.tuhh.sts.team11.client;

import de.tuhh.sts.team11.util.Logger;

import javax.swing.*;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class UserGUI {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private final UserAgent userAgent;
    private JFrame searchForm;

    public UserGUI(final UserAgent userAgent) {
        this.userAgent = userAgent;

        searchForm = SearchGUI.createForm(this);
    }

    // callbacks

    public void loginSuccess() {
        LOG.info("Login success");
    }

    public void loginFailed(String username) {
        new LoginForm(this, username);
    }

    // windows

    public void showLogin() {
        new LoginForm(this);
    }

    public void showNewAuction() {
        new NewAuction(this);
    }

    // events

    public void createAccountClosed() {
        showLogin();
    }

    public void createAccount(final String email, final String username, final String password) {
        userAgent.createNewAccount(email, username, password);
    }

    public void loginClosed() {

    }

    public void login(final String username, final String password) {
        userAgent.login(username, password);
    }

    public void createAuctionClosed() {

    }

    public void createAuction(final String name, final Integer amount, final Integer price, final String type, final
    String direction, final Date endTime, final Integer priceDelta, final Integer timeDelta) {
        userAgent.createAuction(name, amount, price, type, direction, endTime, priceDelta, timeDelta);
    }
}
