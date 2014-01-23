package de.tuhh.sts.team11.server.database;

import org.garret.perst.Indexable;
import org.garret.perst.Persistent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class UserData extends Persistent {
    String email;

    @Indexable(unique = true, caseInsensitive = true)
    String username;
    String password;

    public UserData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
