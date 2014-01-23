package de.tuhh.sts.team11.server.database;

import org.garret.perst.Persistent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class UserData extends Persistent {
    String username;
    String email;
    String passwort;

    public UserData(String username, String email, String passwort) {
        this.username = username;
        this.email = email;
        this.passwort = passwort;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
