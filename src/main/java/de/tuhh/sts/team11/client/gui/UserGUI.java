package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.client.UserAgent;
import de.tuhh.sts.team11.protocol.ListAuctionsReply;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.Types;

import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class UserGUI {
    private static final Logger LOG = Logger.getLogger(UserAgent.class.getName());

    private final UserAgent userAgent;
    private SearchGUI searchGUI;

    public UserGUI(final UserAgent userAgent) {
        this.userAgent = userAgent;

        showLogin();
    }

    // callbacks

    public void loginSuccess() {
        LOG.info("Login success");
        showSearchWindow();
    }

    public void loginFailed(String username) {
        LOG.info("Login failed");
        showLogin(username);
    }

    public void setAuctionList(final List<ListAuctionsReply.Auction> auctions) {
        searchGUI.setAuctionList(auctions);
    }

    // windows

    public void showLogin() {
        LOG.info("show login");
        new LoginForm(this);
    }

    private void showLogin(final String username) {
        LOG.info("show login");
        new LoginForm(this, username);
    }

    public void showCreateAccount() {
        LOG.info("show create account");
        new CreateAccount(this);
    }

    public void showCreateAuction() {
        LOG.info("show create auction");
        new CreateAuction(this);
    }

    public void showSearchWindow() {
        LOG.info("show search");
        searchGUI = new SearchGUI(this);
        userAgent.refreshAuctions();
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

    public void createAuction(final String name, final Integer amount, final Integer price,
                              final Types.AuctionType type, final
    Types.AuctionDirection direction, final Date endTime, final Integer priceDelta, final Integer timeDelta) {
        userAgent.createAuction(name, amount, price, type, direction, endTime, priceDelta, timeDelta);
    }

    public void searchClosed() {

    }
}
