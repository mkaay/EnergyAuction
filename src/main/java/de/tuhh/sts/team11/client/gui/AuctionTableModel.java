package de.tuhh.sts.team11.client.gui;

import de.tuhh.sts.team11.protocol.Auction;
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
    private List<Auction> auctions = new ArrayList<Auction>();
    private String[] columnNames = {"Name", "Amount", "Price", "Type", "End"};

    public void setAuctions(final List<Auction> auctions) {
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
        Auction auction = auctions.get(row);
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
                return auction.getType().equals(Types.AuctionType.DUTCH) ? "Dutch" : "Reverse Dutch";
            case 4:
                return auction.getEndTime();
            default:
                return "";
        }
    }

    public Auction getAuctionFromRow(int row) {
        return auctions.get(row);
    }
}
