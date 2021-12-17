package dto;

import com.google.gson.annotations.Expose;
import entities.DataEntry;
import entities.Ticker;

import java.util.ArrayList;

public class TickerTransferObject {
    @Expose
    private Ticker ticker;
    @Expose
    private ArrayList<DataEntry> dataEntries;

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public ArrayList<DataEntry> getDataEntries() {
        return dataEntries;
    }

    public void setDataEntries(ArrayList<DataEntry> dataEntries) {
        this.dataEntries = dataEntries;
    }

    public TickerTransferObject(Ticker ticker, ArrayList<DataEntry> dataEntries) {
        this.ticker = ticker;
        this.dataEntries = dataEntries;
    }
}
