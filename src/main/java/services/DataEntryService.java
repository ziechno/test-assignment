package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import dao.DataEntryDao;
import dto.TickerTransferObject;
import entities.DataEntry;
import entities.Ticker;
import utils.JSONObjectMapper;
import utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataEntryService {

    private static final String FINANCE_CHART_BASEURL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public ArrayList<TickerTransferObject> fetchTickerData(ArrayList<Ticker> tickerList, String date) throws IOException {

        ArrayList<TickerTransferObject> dailyEntries = new ArrayList<>();
        Long epochDate = Utilities.dateToEpoch(date);
        //Check if provided date is valid
        if(epochDate < Instant.now().getEpochSecond()) {
            DataEntryDao dataEntryDao = new DataEntryDao();
            //Get data from database for ticker list on a given date
            for (Ticker t : tickerList) {
                ArrayList<DataEntry> tickerData = dataEntryDao.getDailyData(t, date);
                if (tickerData.isEmpty()) {
                    //Get data from server if it doesn't exist in database and save it
                    tickerData = getDataFromServer(t, date);
                    dataEntryDao.saveAll(tickerData);
                }
                dailyEntries.add(new TickerTransferObject(t, tickerData));
            }
        }
        return dailyEntries;
    }

    private ArrayList<DataEntry> getDataFromServer(Ticker t, String date) throws IOException {

        //Converting to UNIX timestamp in seconds
        LocalDateTime startDateTime = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusHours(24);
        long startEpoch = Utilities.dateTimeToEpoch(startDateTime);
        long endEpoch = Utilities.dateTimeToEpoch(endDateTime);

        ArrayList<DataEntry> listOfEntries = new ArrayList<>();

        //Get timestamps, close price and open price as JSON
        URL financeChartURL = new URL(FINANCE_CHART_BASEURL + t.getSymbol() +
                "?period1=" + startEpoch + "&period2=" + endEpoch + "&interval=1h");
        URLConnection tickerDataServerConnection = financeChartURL.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(tickerDataServerConnection.getInputStream()));
        String chart = br.readLine();

        //Get JSON Arrays for needed attributes
        ArrayNode timestamps = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "timestamp");
        ArrayNode openPrices = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "open");
        ArrayNode closePrices = (ArrayNode) JSONObjectMapper.getObjectByKey(chart, "close");

        //Create entry for every timestamp and set their values
        for (int i = 0; i < timestamps.size() - 1; i++) {
            DataEntry dataEntry = new DataEntry();
            Timestamp timestamp = Timestamp.valueOf(Utilities.epochToTimestamp(timestamps.get(i).asText()));
            dataEntry.setTimestamp(timestamp);
            dataEntry.setTicker(t);
            dataEntry.setClosePrice(closePrices.get(i).asDouble());
            dataEntry.setOpenPrice(openPrices.get(i).asDouble());
            listOfEntries.add(dataEntry);
        }
       return listOfEntries;
    }
}
