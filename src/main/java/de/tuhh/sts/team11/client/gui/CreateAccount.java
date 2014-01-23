package de.tuhh.sts.team11.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/22/14
 */
public class CreateAccount {
    private final JFrame frame = new JFrame("New Account");
    private final UserGUI gui;

    private JTextField emailField;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createButton;
    private JButton cancelButton;

    private boolean ignoreCloseEvent = false;

    public CreateAccount(final UserGUI gui) {
        this.gui = gui;

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cancel();
            }
        });

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                if (!ignoreCloseEvent) {
                    cancel();
                }
            }
        });

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                submit();
            }
        };

        createButton.addActionListener(actionListener);
        emailField.addActionListener(actionListener);
        usernameField.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
    }

    private void closeFrame() {
        ignoreCloseEvent = true;
        frame.setVisible(false);
        frame.dispose();
    }

    private void submit() {
        if (emailField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            //TODO
        } else {
            closeFrame();

            gui.createAccount(emailField.getText(), usernameField.getText(), passwordField.getText());
        }
    }

    private void cancel() {
        closeFrame();

        gui.createAccountClosed();
    }
}
