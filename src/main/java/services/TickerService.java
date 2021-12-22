package services;

import dao.TickerDao;
import entities.DataEntry;
import entities.Ticker;
import utils.HttpResponseReader;
import utils.Utilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TickerService {
    // Base query URLs
    private static final String QUOTE_SUMMARY_BASEURL = "https://query1.finance.yahoo.com/v11/finance/quoteSummary/";
    private static final String QUOTE_SUMMARY_MODULE = "?modules=assetProfile";
    private static final String FINANCE_QUOTE_BASEURL = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=";

    public ArrayList<DataEntry> getTickerData(String tickerSymbols, String date) throws IOException {
        //Get existing tickers from DB
        TickerDao tickerDao = new TickerDao();
        ArrayList<String> tickerSymbolList = Utilities.parseSymbols(tickerSymbols);
        ArrayList<Ticker> tickerList =  tickerDao.getAllFromList(tickerSymbolList);

        //Remove symbols for tickers that already exist in DB
        for (Ticker t : tickerList) {
            tickerSymbolList.removeIf(n -> (n.equals(t.getSymbol())));
        }

        //Get non-existing ticker entries from Yahoo and save them to DB
        ArrayList<Ticker> newTickers = new ArrayList<>();
        for (String s : tickerSymbolList) {
            Ticker newTicker = getTickerFromServer(s);
            if(newTicker != null){
                newTickers.add(newTicker);
            }
        }
        //Save newly obtained tickers in the DB, and merge them with already existing ones
        tickerDao.saveAll(newTickers);
        tickerList.addAll(newTickers);

        //Get historical data about tickers
        DataEntryService dataEntryService = new DataEntryService();
        return dataEntryService.getHistoricalData(tickerList, date);
    }

    private Ticker getTickerFromServer(String symbol) throws IOException {
        HttpResponseReader httpResponseReader = new HttpResponseReader();
        //State, city, number of employees, full name, year founded, and market cap endpoints
        URL quoteSummaryURL = new URL(QUOTE_SUMMARY_BASEURL + symbol + QUOTE_SUMMARY_MODULE);
        URL financeQuoteURL = new URL(FINANCE_QUOTE_BASEURL + symbol);

        //If the ticker doesn't exist we will get 404 error code
        if(httpResponseReader.getResponseCode(quoteSummaryURL) == 200) {

            String quoteSummary = httpResponseReader.readResponse(quoteSummaryURL);
            String quoteResponse = httpResponseReader.readResponse(financeQuoteURL);

            //Create new ticker instance and assign values
            return new Ticker(symbol, quoteSummary, quoteResponse);
        }
        return null;
    }
}
