package de.tuhh.sts.team11.util;

import java.util.logging.Level;
import java.util.logging.LogManager;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class Logger extends java.util.logging.Logger {
    protected Logger(String name) {
        super(name, null);
    }

    public static Logger getLogger(String name) {
        LogManager m = LogManager.getLogManager();
        Object l = m.getLogger(name);
        if (l == null) {
            m.addLogger(new Logger(name));
        }
        l = m.getLogger(name);
        return (Logger) l;
    }

    public void finest(final String msg, final Throwable thrown) {
        super.log(Level.FINEST, msg, thrown);
    }

    public void finer(final String msg, final Throwable thrown) {
        super.log(Level.FINER, msg, thrown);
    }

    public void fine(final String msg, final Throwable thrown) {
        super.log(Level.FINE, msg, thrown);
    }

    public void info(final String msg, final Throwable thrown) {
        super.log(Level.INFO, msg, thrown);
    }

    public void warning(final String msg, final Throwable thrown) {
        super.log(Level.WARNING, msg, thrown);
    }

    public void severe(final String msg, final Throwable thrown) {
        super.log(Level.SEVERE, msg, thrown);
    }
}
