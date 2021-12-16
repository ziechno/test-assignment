package services;


import dao.TickerDao;
import entities.Ticker;
import utils.JSONObjectMapper;
import utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TickerService {
    // city, state, employees
    private static final String QUOTE_SUMMARY_BASEURL = "https://query1.finance.yahoo.com/v11/finance/quoteSummary/";
    private static final String QUOTE_SUMMARY_MODULE = "?modules=assetProfile";
    // full name, market cap, first trade tade
    private static final String FINANCE_QUOTE_BASEURL = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=";

    public ArrayList<Ticker> fetchTickers(String symbols) throws IOException {
        TickerDao tickerDao = new TickerDao();
        //Get existing tickers from DB
        ArrayList<String> tickerSymbols = Utilities.parseSymbols(symbols);
        ArrayList<Ticker> tickerList = tickerDao.getAllFromList(tickerSymbols);
        //Remove symbols that have DB entry
        for(Ticker t : tickerList){
            tickerSymbols.removeIf(n -> (n.equals(t.getSymbol())));
        }
        //Get non-existing tickers from server
        for(String s : tickerSymbols){
            Ticker ticker = getTickerFromServer(s);
            tickerDao.save(ticker);
            tickerList.add(ticker);
        }
    return tickerList;
    }

    private Ticker getTickerFromServer(String s) throws IOException {
        URL quoteSummaryURL = new URL(QUOTE_SUMMARY_BASEURL + s + QUOTE_SUMMARY_MODULE);
        URLConnection tickerServerConnection = quoteSummaryURL.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(tickerServerConnection.getInputStream()));
        String quoteSummary = br.readLine();

        URL financeQuoteURL = new URL(FINANCE_QUOTE_BASEURL + s);
        tickerServerConnection = financeQuoteURL.openConnection();

        br = new BufferedReader(new InputStreamReader(tickerServerConnection.getInputStream()));
        String quoteResponse = br.readLine();

        Ticker ticker = new Ticker();
        ticker.setSymbol(s);
        ticker.setFullName(JSONObjectMapper.getObjectByKey(quoteResponse, "longName").asText());
        ticker.setState(JSONObjectMapper.getObjectByKey(quoteSummary, "state").asText());
        ticker.setCity(JSONObjectMapper.getObjectByKey(quoteSummary, "city").asText());
        Long yearMilliseconds = JSONObjectMapper.getObjectByKey(quoteResponse, "firstTradeDateMilliseconds").asLong();
        ticker.setYearFounded(Utilities.milisecondToDate(yearMilliseconds));
        ticker.setEmployeeNumber(JSONObjectMapper.getObjectByKey(quoteSummary, "fullTimeEmployees").asInt());

        return ticker;
    }
}
