package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.util.Types;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;


/**
 * Created by mkaay on 21.01.14.
 */
public class CreateAuction {
    private final JFrame frame = new JFrame("New Auction");
    private final UserGUI gui;

    private JPanel contentPane;
    private JTextField nameField;
    private JSpinner amountSpinner;
    private JComboBox typeSelect;
    private JSpinner unitPriceSpinner;
    private JSpinner priceDeltaSpinner;
    private JSpinner timeDeltaSpinner;
    private JSpinner endTimeSpinner;
    private JButton createButton;
    private JSpinner priceTotalSpinner;

    private boolean ignoreCloseEvent = false;

    public CreateAuction(UserGUI userGUI) {
        gui = userGUI;

        Vector<String> typeList = new Vector<String>();
        typeList.add("Dutch");
        typeList.add("Reverse Dutch");

        typeSelect.setModel(new DefaultComboBoxModel(typeList));

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR, 1);
        Date initDate = calendar.getTime();

        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();

        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();

        endTimeSpinner.setModel(new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR));
        amountSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        unitPriceSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        priceDeltaSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        timeDeltaSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

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

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submit();
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

    private void closeFrame() {
        ignoreCloseEvent = true;
        frame.setVisible(false);
        frame.dispose();
    }

    private void cancel() {
        closeFrame();

        gui.createAuctionClosed();
    }

    private void submit() {
        closeFrame();

        gui.createAuction(getName(), getAmount(), getPrice(), getType(), getEndTime(),
                getPriceDelta(), getTimeDelta());
    }

    private Integer getTimeDelta() {
        return (Integer) timeDeltaSpinner.getValue();
    }

    private Integer getPriceDelta() {
        return (Integer) priceDeltaSpinner.getValue();
    }

    private Integer getPrice() {
        return (Integer) unitPriceSpinner.getValue();
    }

    private Date getEndTime() {
        return (Date) endTimeSpinner.getModel().getValue();
    }

    private Types.AuctionType getType() {
        return typeSelect.getSelectedIndex() == 0 ? Types.AuctionType.DUTCH : Types.AuctionType.REVERSE_DUTCH;
    }

    private Integer getAmount() {
        return (Integer) amountSpinner.getValue();
    }

    private String getName() {
        return nameField.getText();
    }
}
