package de.tuhh.sts.team11.client;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class SearchGUI {

    private JTable auctionList;
    private JButton newAuctionButton;
    private JPanel panel1;
    private UserGUI gui;

    public SearchGUI(UserGUI userGUI) {
        gui = userGUI;

        TableModel dataModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int i) {
                switch (i) {
                    case 0: return "Name";
                    case 1: return "Menge";
                    case 2: return "Typ";
                    case 3: return "Ende";
                    case 4: return "Preis";
                    case 5: return "An-/Verkauf";
                    default: return "";
                }
            }

            public int getColumnCount() { return 8; }
            public int getRowCount() { return 10;}
            public Object getValueAt(int row, int col) { return new Integer(row*col); }
        };
        auctionList.setModel(dataModel);

        newAuctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.newAuction();
            }
        });
    }

    public static JFrame createForm(UserGUI userGUI) {
        JFrame frame = new JFrame("Search");
        frame.setContentPane(new SearchGUI(userGUI).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }
}
