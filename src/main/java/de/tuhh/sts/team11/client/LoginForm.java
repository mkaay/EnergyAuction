package de.tuhh.sts.team11.client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class LoginForm {
    private JTextField usernameField;
    private JPanel panel1;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createNewAccountButton;

    abstract class TextChangedListener implements DocumentListener {
        @Override
        public void insertUpdate(final DocumentEvent e) {
            textChanged(e);
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            textChanged(e);
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            textChanged(e);
        }

        public abstract void textChanged(final DocumentEvent e);
    }

    private LoginForm() {
        loginButton.setEnabled(false);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (loginButton.isEnabled()) {
                    handleLogin();
                }
            }
        };

        usernameField.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
        loginButton.addActionListener(actionListener);

        TextChangedListener textChangedListener = new TextChangedListener() {
            @Override
            public void textChanged(final DocumentEvent e) {
                loginButton.setEnabled(!(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()));
            }
        };

        usernameField.getDocument().addDocumentListener(textChangedListener);
        passwordField.getDocument().addDocumentListener(textChangedListener);
    }

    private void handleLogin() {
        enableForm(false);
        loginButton.setEnabled(false);
    }

    private void enableForm(final boolean enable) {
        usernameField.setEnabled(enable);
        passwordField.setEnabled(enable);
    }

    public static JFrame createForm() {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }
}
