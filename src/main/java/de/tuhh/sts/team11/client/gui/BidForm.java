package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.protocol.Auction;
import de.tuhh.sts.team11.util.Types;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
public class BidForm {
    private final JFrame frame = new JFrame("Bid");
    private final UserGUI gui;
    private final Auction auctionData;
    private JPanel contentPane;
    private JSpinner amountSpinner;
    private JSpinner unitPriceSpinner;
    private JButton submitButton;
    private JButton cancelButton;
    private JSpinner priceTotalSpinner;

    private boolean ignoreCloseEvent = false;

    public BidForm(UserGUI userGUI, final Auction auctionData) {
        gui = userGUI;
        this.auctionData = auctionData;

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cancel();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                submit();
            }
        });

        amountSpinner.setModel(new SpinnerNumberModel(auctionData.getAmount(), 0, auctionData.getAmount(), 1));
        if (auctionData.getType() == Types.AuctionType.REVERSE_DUTCH) {
            unitPriceSpinner.setModel(new SpinnerNumberModel(auctionData.getPrice(), auctionData.getPrice(),
                    Integer.MAX_VALUE, auctionData.getPriceDelta()));
            amountSpinner.setEnabled(false);
        } else {
            unitPriceSpinner.setModel(new SpinnerNumberModel(auctionData.getPrice(), 1, auctionData.getPrice(),
                    auctionData.getPriceDelta()));
        }

        amountSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                recalculatePrice();
            }
        });
        unitPriceSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                recalculatePrice();
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
    }

    private void recalculatePrice() {
        int amount = (Integer) amountSpinner.getValue();
        int unitPrice = (Integer) unitPriceSpinner.getValue();
        priceTotalSpinner.setValue(amount * unitPrice);
    }

    private void submit() {
        closeFrame();

        gui.submitBid(auctionData, (Integer) amountSpinner.getValue(), (Integer) unitPriceSpinner.getValue());
    }

    private void closeFrame() {
        ignoreCloseEvent = true;
        frame.setVisible(false);
        frame.dispose();
    }

    private void cancel() {
        closeFrame();

        gui.cancelBid();
    }
}
