package de.tuhh.sts.team11.protocol;

import org.garret.perst.Persistent;

import java.io.Serializable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class UserData extends Persistent implements Serializable {
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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
