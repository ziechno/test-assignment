package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import dao.DataEntryDao;
import entities.DataEntry;
import entities.Ticker;
import utils.HttpResponseReader;
import utils.JSONObjectMapper;
import utils.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataEntryService {

    private static final String FINANCE_CHART_BASEURL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public ArrayList<DataEntry> getHistoricalData(ArrayList<Ticker> tickerList, String date) throws IOException {

        ArrayList<DataEntry> dailyEntries = new ArrayList<>();
        ArrayList<DataEntry> newDailyEntries = new ArrayList<>();
        Long epochDate = Utilities.dateToEpoch(date);

        //Check if provided date is valid (doesn't point to the future)
        if(epochDate < Instant.now().getEpochSecond()) {
            DataEntryDao dataEntryDao = new DataEntryDao();

            //Get data from database for ticker list on a given date
            for (Ticker t : tickerList) {
                ArrayList<DataEntry> dailyData = dataEntryDao.getDailyData(t, date);
                //If the dailyData is empty, that means we currently don't have data on a specified date, and we must fetch it from Yahoo
                if (dailyData.isEmpty()) {
                    dailyData = getDataFromServer(t, date);
                    newDailyEntries.addAll(dailyData);
                }
                dailyEntries.addAll(dailyData);
            }
            //Save new daily data in one take
            dataEntryDao.saveAll(newDailyEntries);
        }
        return dailyEntries;
    }

    private ArrayList<DataEntry> getDataFromServer(Ticker ticker, String date) throws IOException {

        ArrayList<DataEntry> listOfEntries = new ArrayList<>();

        //Converting to UNIX timestamp in seconds
        LocalDateTime startDateTime = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusHours(24);
        long startEpoch = Utilities.dateTimeToEpoch(startDateTime);
        long endEpoch = Utilities.dateTimeToEpoch(endDateTime);

        //Timestamps, close price and open price endpoint
        URL financeChartURL = new URL(FINANCE_CHART_BASEURL + ticker.getSymbol() + "?period1=" + startEpoch + "&period2=" + endEpoch + "&interval=1h");

        HttpResponseReader httpResponseReader = new HttpResponseReader();
        String financeChart = httpResponseReader.readResponse(financeChartURL);

        //Get JSON Arrays for needed attributes
        ArrayNode timestamps = (ArrayNode) JSONObjectMapper.getObjectByKey(financeChart, "timestamp");
        ArrayNode openPrices = (ArrayNode) JSONObjectMapper.getObjectByKey(financeChart, "open");
        ArrayNode closePrices = (ArrayNode) JSONObjectMapper.getObjectByKey(financeChart, "close");

        //Create entry for every timestamp and set their values
        for (int i = 0; i < timestamps.size() - 1; i++) {
            Timestamp timestamp = Timestamp.valueOf(Utilities.epochToTimestamp(timestamps.get(i).asText()));
            Double closePrice = closePrices.get(i).asDouble();
            Double openPrice = openPrices.get(i).asDouble();
            DataEntry dataEntry = new DataEntry(ticker, timestamp, closePrice, openPrice);
            listOfEntries.add(dataEntry);
        }
       return listOfEntries;
    }
}

