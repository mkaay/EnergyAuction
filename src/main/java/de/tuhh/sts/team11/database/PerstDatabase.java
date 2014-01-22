package de.tuhh.sts.team11.database;

import de.tuhh.sts.team11.protocol.AuctionData;
import de.tuhh.sts.team11.protocol.UserData;
import org.garret.perst.Database;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;


/**
 * Created with IntelliJ IDEA. User: mkaay Date: 10.01.14 Time: 14:02 To change this template use File | Settings | File
 * Templates.
 */
public class PerstDatabase {

    public class BidData extends Persistent {
        int preis;
        long zeit;

        protected BidData(int preis, long zeit) {
            this.preis = preis;
            this.zeit = zeit;
        }

        int getPreis() {
            return preis;
        }

        void setPreis(int preis) {
            this.preis = preis;
        }

        long getZeit() {
            return zeit;
        }

        void setZeit(long zeit) {
            this.zeit = zeit;
        }
    }

    private Storage storage;
    private Database db;
    private static PerstDatabase instance;
    private static String defaultDatabase = "sample.dbs";

    // should also have a default path, otherwise will be created in the eclipse
    // project root

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

    protected Database getDB() {
        return db;
    }

    protected Storage getStorage() {
        return storage;
    }

    protected void closeDB() {
        db.dropTable(AuctionData.class);
        db.dropTable(UserData.class);
        db.dropTable(BidData.class);
        db.commitTransaction();
        storage.close();
    }

    private void retrieveData() {

    }

    static public void main(String[] args) {
        // get default instance of the storage
        PerstDatabase m = PerstDatabase.INSTANCE();
        m.retrieveData();
        m.closeDB();
    }
}
