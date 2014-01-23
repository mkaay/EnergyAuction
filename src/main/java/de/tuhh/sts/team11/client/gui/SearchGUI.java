package de.tuhh.sts.team11.client.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
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
public class SearchGUI {
    private final JFrame frame = new JFrame("Search");
    private final UserGUI gui;

    private JTable auctionList;
    private JButton newAuctionButton;
    private JPanel contentPane;

    private boolean ignoreCloseEvent = false;

    public SearchGUI(UserGUI userGUI) {
        gui = userGUI;

        TableModel dataModel = new AbstractTableModel() {
            @Override
            public String getColumnName(int i) {
                switch (i) {
                    case 0:
                        return "Name";
                    case 1:
                        return "Amount";
                    case 2:
                        return "AuctionType";
                    case 3:
                        return "End";
                    case 4:
                        return "Price";
                    case 5:
                        return "Buy/Sell";
                    default:
                        return "";
                }
            }

            public int getColumnCount() {
                return 8;
            }

            public int getRowCount() {
                return 10;
            }

            public Object getValueAt(int row, int col) {
                return new Integer(row * col);
            }
        };
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
}
