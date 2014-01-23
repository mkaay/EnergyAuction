package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.server.database.AuctionData;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class SearchGUI {
    private final JFrame frame = new JFrame("Search");
    private final UserGUI gui;

    private JTable auctionList;
    private JButton newAuctionButton;
    private JPanel contentPane;

    private boolean ignoreCloseEvent = false;
    private final AuctionModel dataModel;

    public SearchGUI(UserGUI userGUI) {
        gui = userGUI;

        dataModel = new AuctionModel();

        auctionList.setModel(dataModel);

        newAuctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.showCreateAuction();
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

    private void cancel() {
        closeFrame();

        gui.searchClosed();
    }

    private void closeFrame() {
        ignoreCloseEvent = true;
        frame.setVisible(false);
        frame.dispose();
    }

    public void setAuctionList(final List<AuctionData> auctions) {
        dataModel.setAuctions(auctions);
    }

    private class AuctionModel extends AbstractTableModel {
        private List<AuctionData> auctions = new ArrayList<AuctionData>();
        private String[] columnNames = {"Name", "Amount", "Price", "Type", "End", "Buy/Sell"};

        public void setAuctions(final List<AuctionData> auctions) {
            this.auctions = auctions;
            fireTableDataChanged();
        }

        @Override
        public String getColumnName(int i) {
            return columnNames[i];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return auctions.size();
        }

        public Object getValueAt(int row, int col) {
            AuctionData auction = auctions.get(row);
            if (auction == null) {
                return "";
            }

            switch (col) {
                case 0:
                    return auction.getName();
                case 1:
                    return auction.getAmount();
                case 2:
                    return auction.getPrice();
                case 3:
                    return "";//auction.getAuctionType().equals(Types.AuctionType.DUTCH) ? "Dutch" : "Reverse Dutch";
                case 4:
                    return auction.getEndTime();
                case 5:
                    return "";//auction.getAuctionDirection().equals(Types.AuctionDirection.BUY) ? "Buy" : "Sell";
                default:
                    return "";
            }
        }
    }
}
