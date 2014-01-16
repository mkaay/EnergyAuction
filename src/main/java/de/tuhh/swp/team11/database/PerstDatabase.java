package de.tuhh.swp.team11.database;

import org.garret.perst.Database;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

/**
 * Created with IntelliJ IDEA.
 * User: mkaay
 * Date: 10.01.14
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class PerstDatabase {
    class Subclass extends Persistent {
        String name;

        public Subclass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    class AuctionData extends Persistent {

        String name;
        int energieMenge;
        String typ;
        long start;
        long ende;
        int preis;
        int preisDelta;
        int zeitDelta;
        boolean kaufen;

        AuctionData(String name, int energieMenge, String typ, long start, long ende, int preis, int preisDelta, int zeitDelta, boolean kaufen) {
            this.name = name;
            this.energieMenge = energieMenge;
            this.typ = typ;
            this.start = start;
            this.ende = ende;
            this.preis = preis;
            this.preisDelta = preisDelta;
            this.zeitDelta = zeitDelta;
            this.kaufen = kaufen;

            PerstDatabase.INSTANCE().getDB().addRecord(this);
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        int getEnergieMenge() {
            return energieMenge;
        }

        void setEnergieMenge(int energieMenge) {
            this.energieMenge = energieMenge;
        }

        String getTyp() {
            return typ;
        }

        void setTyp(String typ) {
            this.typ = typ;
        }

        long getStart() {
            return start;
        }

        void setStart(long start) {
            this.start = start;
        }

        long getEnde() {
            return ende;
        }

        void setEnde(long ende) {
            this.ende = ende;
        }

        int getPreis() {
            return preis;
        }

        void setPreis(int preis) {
            this.preis = preis;
        }

        int getPreisDelta() {
            return preisDelta;
        }

        void setPreisDelta(int preisDelta) {
            this.preisDelta = preisDelta;
        }

        int getZeitDelta() {
            return zeitDelta;
        }

        void setZeitDelta(int zeitDelta) {
            this.zeitDelta = zeitDelta;
        }

        boolean isKaufen() {
            return kaufen;
        }

        void setKaufen(boolean kaufen) {
            this.kaufen = kaufen;
        }

    }

    class UserData extends Persistent {
        String username;
        String email;
        String passwort;

        UserData(String username, String email, String passwort) {
            this.username = username;
            this.email = email;
            this.passwort = passwort;

            PerstDatabase.INSTANCE().getDB().addRecord(this);
        }

        String getUsername() {
            return username;
        }

        void setUsername(String username) {
            this.username = username;
        }

        String getEmail() {
            return email;
        }

        void setEmail(String email) {
            this.email = email;
        }

        String getPasswort() {
            return passwort;
        }

        void setPasswort(String passwort) {
            this.passwort = passwort;
        }
    }

    class BidData extends Persistent {
        int preis;
        long zeit;

        BidData(int preis, long zeit) {
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
        if (instance == null)
            instance = new PerstDatabase(defaultDatabase);
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
