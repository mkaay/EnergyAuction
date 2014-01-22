package de.tuhh.sts.team11.client.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class LoginForm {
    private final JFrame frame = new JFrame("Login");
    private final UserGUI gui;

    private JTextField usernameField;
    private JPanel contentPane;
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

    public LoginForm(final UserGUI userGUI) {
        this(userGUI, "");
    }

    public LoginForm(final UserGUI userGUI, final String username) {
        this.gui = userGUI;

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

        usernameField.setText(username);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                cancel();
            }
        });
    }

    private void handleLogin() {
    }

    private void enableForm(final boolean enable) {
        usernameField.setEnabled(enable);
        passwordField.setEnabled(enable);
    }

    private void closeFrame() {
        frame.setVisible(false);
        frame.dispose();
    }

    private void cancel() {
        closeFrame();

        gui.loginClosed();
    }

    private void submit() {
        closeFrame();

        gui.login(usernameField.getText(), passwordField.getText());
    }
}
