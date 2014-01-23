package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.protocol.ListAuctionsReply;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private final AuctionTableModel dataModel;

    public SearchGUI(UserGUI userGUI) {
        gui = userGUI;

        dataModel = new AuctionTableModel();

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

    public void setAuctionList(final List<ListAuctionsReply.Auction> auctions) {
        dataModel.setAuctions(auctions);
    }

}
