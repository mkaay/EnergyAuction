package de.tuhh.sts.team11.server.database;

import org.garret.perst.Database;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;


/**
 * Created with IntelliJ IDEA. User: mkaay Date: 10.01.14 Time: 14:02 To change this template use File | Settings | File
 * Templates.
 */
public class PerstDatabase {
    private Storage storage;
    private Database db;
    private static PerstDatabase instance;
    private static String defaultDatabase = "res/data.dbs";

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

    public UserData getUserData(String username) {
        for (UserData userData : db.<UserData>select(UserData.class, String.format("username = '%s'", username))) {
            return userData;
        }

        return null;
    }
}
