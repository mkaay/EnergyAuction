package de.tuhh.sts.team11.server.database;

import de.tuhh.sts.team11.server.exceptions.UsernameAlreadyTakenException;
import de.tuhh.sts.team11.util.Logger;
import org.garret.perst.Database;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;


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
}
