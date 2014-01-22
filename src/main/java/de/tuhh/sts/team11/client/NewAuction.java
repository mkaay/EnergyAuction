package de.tuhh.sts.team11.client;

import de.tuhh.sts.team11.protocol.AuctionData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * Created by mkaay on 21.01.14.
 */
public class NewAuction {
    private JPanel panel1;
    private JTextField nameField;
    private JSpinner amountSpinner;
    private JComboBox typeSelect;
    private JComboBox directionSelect;
    private JSpinner priceSpinner;
    private JSpinner priceDeltaSpinner;
    private JSpinner timeDeltaSpinner;
    private JSpinner endTimeSpinner;
    private JButton createButton;

    private final UserGUI gui;

    public NewAuction(UserGUI userGUI) {
        gui = userGUI;

        Vector<String> typeList = new Vector<String>();
        typeList.add("Dutch");
        typeList.add("Reverse Dutch");

        Vector<String> directionList = new Vector<String>();
        directionList.add("Buy");
        directionList.add("Sell");

        typeSelect.setModel(new DefaultComboBoxModel(typeList));
        directionSelect.setModel(new DefaultComboBoxModel(directionList));

        Calendar calendar = new GregorianCalendar();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();

        endTimeSpinner.setModel(new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR));

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.createAuction(toData());
            }
        });
    }

    public AuctionData toData() {
        String type = typeSelect.getSelectedIndex() == 0 ? "dutch" : "reversedutch";
        boolean direction = directionSelect.getSelectedIndex() == 0;

        AuctionData data = new AuctionData(nameField.getText(), (Integer) amountSpinner.getValue(), type, GregorianCalendar.getInstance().getTime(), (Date) endTimeSpinner.getModel().getValue(), (Integer) priceSpinner.getValue(), (Integer) priceDeltaSpinner.getValue(), (Integer) timeDeltaSpinner.getValue(), direction);
        return data;
    }

    public static JFrame createForm(UserGUI userGUI) {
        JFrame frame = new JFrame("New Auction");
        frame.setContentPane(new NewAuction(userGUI).panel1);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }
}
