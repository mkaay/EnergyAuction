package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.client.UserAgent;
import de.tuhh.sts.team11.protocol.Auction;
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
    private MainView mainView;

    public UserGUI(final UserAgent userAgent) {
        this.userAgent = userAgent;

        mainView = new MainView(this);
        showLogin();
    }

    // callbacks

    public void loginSuccess() {
        LOG.info("Login success");
        showMainWindow();
    }

    public void loginFailed(String username) {
        LOG.info("Login failed");
        showLogin(username);
    }

    public void setAuctionList(final List<Auction> auctions) {
        mainView.setAuctionList(auctions);
    }

    public void createAuctionFailed() {
        // should only happen if not logged in
    }

    public void createAuctionSuccess() {

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

    public void showMainWindow() {
        LOG.info("show main");
        mainView.show();
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
                              final Types.AuctionType type, final Date endTime, final Integer priceDelta,
                              final Integer timeDelta) {
        userAgent.createAuction(name, amount, price, type, endTime, priceDelta, timeDelta);
    }

    public void searchClosed() {

    }

    public void showBidForm(final Auction auctionData) {
        new BidForm(this, auctionData);
    }

    public void cancelBid() {

    }

    public void submitBid(final Auction auctionData, final Integer amount, final Integer price) {
        LOG.info(String.format("amount: %s price: %s", amount, price));
        userAgent.createBidAgent(auctionData, amount, price);
    }
}
