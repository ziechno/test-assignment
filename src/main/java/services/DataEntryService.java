package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import dao.DataEntryDao;
import entities.DataEntry;
import entities.Ticker;
import utils.JSONObjectMapper;
import utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataEntryService {

    private static final String FINANCE_CHART_BASEURL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public Map<Ticker, ArrayList<DataEntry>> fetchTickerData(ArrayList<Ticker> tickerList, String date) throws IOException {
        DataEntryDao dataEntryDao = new DataEntryDao();
        Map<Ticker, ArrayList<DataEntry>> dailyEntries = new HashMap<>();

        for(Ticker t : tickerList){
            ArrayList<DataEntry> tickerData = dataEntryDao.getDailyData(t, date);
            if(tickerData.isEmpty()){
                tickerData = getDataFromServer(t, date);
                dataEntryDao.saveAll(tickerData);
            }
            dailyEntries.put(t, tickerData);
        }
        return dailyEntries;
    }

    private ArrayList<DataEntry> getDataFromServer(Ticker t, String date) throws IOException {
        String period = Utilities.dateToEpoch(date);

        URL financeChartURL = new URL(FINANCE_CHART_BASEURL + t.getSymbol() +
                "?period1=" + period + "&period2=" + period + "&range=1d");

        URLConnection tickerDataServerConnection = financeChartURL.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(tickerDataServerConnection.getInputStream()));

        String chart = br.readLine();

        ArrayNode timestamps = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "timestamp");
        ArrayNode openPrices = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "open");
        ArrayNode closePrices = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "close");

        ArrayList<DataEntry> listOfEntries = new ArrayList<>();

        for(int i = 0; i<timestamps.size()-1; i++){
            DataEntry dataEntry = new DataEntry();
            LocalDateTime timestamp = Utilities.epochToTimestamp(timestamps.get(i).asText());
            dataEntry.setTimestamp(timestamp);
            dataEntry.setTicker(t);
            dataEntry.setClosePrice(closePrices.get(i).asDouble());
            dataEntry.setOpenPrice(openPrices.get(i).asDouble());
            listOfEntries.add(dataEntry);
        }
        return listOfEntries;
    }
}
