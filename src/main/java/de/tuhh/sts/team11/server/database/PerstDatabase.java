package de.tuhh.sts.team11.server.database;

import de.tuhh.sts.team11.server.exceptions.UsernameAlreadyTakenException;
import de.tuhh.sts.team11.util.Logger;
import de.tuhh.sts.team11.util.Types;
import org.garret.perst.Database;
import org.garret.perst.IterableIterator;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created with IntelliJ IDEA. User: mkaay Date: 10.01.14 Time: 14:02 To change this template use File | Settings | File
 * Templates.
 */
public class PerstDatabase {
    private static final Logger LOG = Logger.getLogger(PerstDatabase.class.getName());

    private final Storage storage;
    private final Database db;
    private static PerstDatabase instance;
    private static final String defaultDatabase = "res/data.dbs";

    public static PerstDatabase INSTANCE() {
        if (instance == null) {
            instance = new PerstDatabase(defaultDatabase);
        }
        return instance;
    }

    private PerstDatabase(String dbName) {
        storage = StorageFactory.getInstance().createStorage();
        storage.open(dbName, 1024);
        db = new Database(storage, false);
        instance = this;
    }

    protected void closeDB() {
        storage.close();
    }

    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public UserData getUser(String username) {
        for (UserData userData : db.<UserData>select(UserData.class, String.format("username='%s'", username))) {
            return userData;
        }

        return null;
    }

    public UserData createNewUser(final String email, final String username,
                                  final String password) throws UsernameAlreadyTakenException {
        UserData userData = new UserData(email, username, password);
        db.beginTransaction();
        if (!db.addRecord(userData)) {
            LOG.info("Duplicate UserData");
            db.rollbackTransaction();

            throw new UsernameAlreadyTakenException();
        }

        db.commitTransaction();
        return userData;
    }

    public AuctionData createAuction(final String name, final Integer amount, final Integer price,
                                     final Types.AuctionType
                                             auctionType,
                                     final Date endTime, final Integer priceDelta,
                                     final Integer timeDelta) {
        Date startTime = GregorianCalendar.getInstance().getTime();
        AuctionData auctionData = new AuctionData(name, amount, price, auctionType, startTime,
                endTime, priceDelta, timeDelta);
        db.beginTransaction();
        db.addRecord(auctionData);
        db.commitTransaction();

        return auctionData;
    }

    public IterableIterator<AuctionData> getAuctions() {
        return db.<AuctionData>select(AuctionData.class, "");
    }

    public IterableIterator<AuctionData> getActiveAuctions() {
        return db.<AuctionData>select(AuctionData.class, "amount > 0");
    }

    public Storage getStorage() {
        return storage;
    }

    public BidData createBid(final int price, final int amount, final AuctionData auctionData,
                             final UserData userData) {
        BidData bidData = new BidData(price, amount, auctionData,
                GregorianCalendar.getInstance().getTime(), userData);
        auctionData.addBid(bidData);
        db.beginTransaction();
        db.addRecord(bidData);
        auctionData.store();
        db.commitTransaction();
        return bidData;
    }

    public AuctionData getAuctionFromOid(final Integer oid) {
        for (AuctionData auctionData : db.<AuctionData>select(AuctionData.class, String.format("oid=%d", oid))) {
            return auctionData;
        }

        return null;
    }

    public BidData getBid(final int oid) {
        for (BidData bidData : db.<BidData>select(BidData.class, String.format("oid=%d", oid))) {
            return bidData;
        }

        return null;
    }

    public IterableIterator<BidData> getBids() {
        return db.<BidData>select(BidData.class, "");
    }

    public Database getDB() {
        return db;
    }
}
