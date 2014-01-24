package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.protocol.Auction;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/16/14
 */
public class MainView {
    private final JFrame frame = new JFrame("Search");
    private final UserGUI gui;

    private JTable auctionList;
    private JButton newAuctionButton;
    private JPanel contentPane;

    private boolean ignoreCloseEvent = false;
    private final AuctionTableModel dataModel;
    private final JPopupMenu popupMenu;

    public MainView(UserGUI userGUI) {
        gui = userGUI;

        dataModel = new AuctionTableModel();

        popupMenu = new JPopupMenu();
        popupMenu.add("Bid").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gui.showBidForm(dataModel.getAuctionFromRow(auctionList.getSelectedRow()));
            }
        });
        auctionList.setModel(dataModel);
        auctionList.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                int r = auctionList.rowAtPoint(e.getPoint());
                if (r >= 0 && r < auctionList.getRowCount()) {
                    auctionList.setRowSelectionInterval(r, r);
                } else {
                    auctionList.clearSelection();
                }

                int rowindex = auctionList.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }

                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    if (dataModel.getAuctionFromRow(auctionList.getSelectedRow()).getEndTime().after
                            (GregorianCalendar.getInstance().getTime())) {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                int rowindex = auctionList.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }

                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        newAuctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.showCreateAuction();
            }
        });

        frame.setContentPane(contentPane);
        frame.pack();
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
        frame.dispose();
    }

    public void setAuctionList(final List<Auction> auctions) {
        dataModel.setAuctions(auctions);
    }

    public void show() {
        frame.setVisible(true);
    }
}
