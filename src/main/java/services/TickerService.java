package services;

import dao.TickerDao;
import entities.Ticker;
import org.json.JSONObject;
import utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TickerService {
    // city, state, employees
    private static final String QUOTE_SUMMARY_BASEURL = "https://query1.finance.yahoo.com/v11/finance/quoteSummary/";
    private static final String QUOTE_SUMMARY_MODULE = "?modules=assetProfile";
    // full name, market cap, first trade tade
    private static final String FINANCE_QUOTE_BASEURL = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=";
    private static final String FINANCE_CHART_BASEURL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    private TickerDao tickerDao;

    public List<Ticker> fetchTickers(List<String> symbols) throws IOException {
        tickerDao = new TickerDao();
        List<Ticker> tickerList = new ArrayList<Ticker>();

        for(String s : symbols){
            Ticker ticker = tickerDao.findBySymbol(s);
            if(ticker == null){
                System.out.println("If from service");
                 ticker = getTickerFromServer(s);
                 //tickerDao.save(ticker);
            }
            //tickerList.add(ticker);
        }
    return tickerList;
    }

    private Ticker getTickerFromServer(String s) throws IOException {
        URL quoteSummaryURL = new URL(QUOTE_SUMMARY_BASEURL + s + QUOTE_SUMMARY_MODULE);
        URLConnection tickerServerConnection = quoteSummaryURL.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(tickerServerConnection.getInputStream()));

        String quoteSummary = br.readLine();
        JSONObject assetProfile = Utilities.getAssetProfileJSON(quoteSummary);

        URL financeQuoteURL = new URL(FINANCE_QUOTE_BASEURL + s);
        tickerServerConnection = financeQuoteURL.openConnection();
        br = new BufferedReader(new InputStreamReader(tickerServerConnection.getInputStream()));

        String quoteResponse = br.readLine();
        JSONObject assetInfo = Utilities.getAssetInfoJSON(quoteResponse);

        Ticker ticker = new Ticker();
        ticker.setSymbol(s);
        ticker.setFull_name(assetInfo.getString("longName"));
        ticker.setState(assetProfile.getString("state"));
        ticker.setCity(assetProfile.getString("city"));
        ticker.setEmployeeNumber(assetProfile.getInt("fullTimeEmployees"));
        Integer yearFounded = Utilities.milisecondToDate(assetInfo.getLong("firstTradeDateMilliseconds"));
        ticker.setYear_founded(yearFounded);

        tickerDao.save(ticker);
        return null;
    }

    private void createTickerProfile(Ticker t, String i ){

    /*    String city = assetProfile.getString("city");
        String state = assetProfile.getString("state");
        int employees = assetProfile.getInt("fullTimeEmployees");
        System.out.println(city);
        System.out.println(state);
        System.out.println(employees);*/
       // t.setCity(assetProfile.getString("city"));
        // t.setState(assetProfile.getInt("fullTimeEmployees"));
       // t.setEmployeeNumber(assetProfile.getInt("fullTimeEmployees"));
    }
}
