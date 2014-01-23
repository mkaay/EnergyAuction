package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.protocol.ListAuctionsReply;
import de.tuhh.sts.team11.util.Types;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author mkaay
 * @since 1/23/14
 */
class AuctionTableModel extends AbstractTableModel {
    private List<ListAuctionsReply.Auction> auctions = new ArrayList<ListAuctionsReply.Auction>();
    private String[] columnNames = {"Name", "Amount", "Price", "Type", "End", "Buy/Sell"};

    public void setAuctions(final List<ListAuctionsReply.Auction> auctions) {
        this.auctions = auctions;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return auctions.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        ListAuctionsReply.Auction auction = auctions.get(row);
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
                return auction.getAuctionType().equals(Types.AuctionType.DUTCH) ? "Dutch" : "Reverse Dutch";
            case 4:
                return auction.getEndTime();
            case 5:
                return auction.getAuctionDirection().equals(Types.AuctionDirection.BUY) ? "Buy" : "Sell";
            default:
                return "";
        }
    }
}
