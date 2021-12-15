package services;

import dao.TickerDataDao;
import entities.Ticker;
import entities.TickerData;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Utilities;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TickerDataService {

    private static final String FINANCE_CHART_BASEURL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public Map<Ticker, ArrayList<TickerData>> fetchTickerData(ArrayList<Ticker> tickerList, String date) throws IOException {
        TickerDataDao tickerDataDao = new TickerDataDao();
        Map<Ticker, ArrayList<TickerData>> mappedData = new HashMap<Ticker, ArrayList<TickerData>>();

        for (Ticker t : tickerList) {
            ArrayList<TickerData> tickerData = tickerDataDao.getTickerDataOnDate(t, date);
            if(tickerData.isEmpty()){
                tickerData = getDataFromServer(t, date);
            }
            mappedData.put(t, tickerData);
        }
        return mappedData;
    }

    private ArrayList<TickerData> getDataFromServer(Ticker t, String date) throws IOException {
        String period = Utilities.dateToEpoch(date);
        URL financeChartURL = new URL(FINANCE_CHART_BASEURL + t.getSymbol() +
                "?period1=" + period + "&period2=" + period + "&range=1d");
        System.out.println(financeChartURL);
        URLConnection tickerDataServerConnection = financeChartURL.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(tickerDataServerConnection.getInputStream()));

        String chart = br.readLine();
        JSONArray timestamps = Utilities.getTimestamps(chart);
        JSONArray quote = Utilities.getQuote(chart);
        JSONObject quoteObject = quote.getJSONObject(0);
        JSONArray openPrice = quoteObject.getJSONArray("open");
        JSONArray closePrice = quoteObject.getJSONArray("close");

        TickerDataDao tickerDataDao = new TickerDataDao();
        ArrayList<TickerData> tickerDataList = new ArrayList<TickerData>();

        for (int i = 0; i<timestamps.length(); i++){
            TickerData tickerData = new TickerData();
            tickerData.setTime(Utilities.epochToLocalTime((timestamps.get(i)).toString()));
            tickerData.setOpenPrice(openPrice.getDouble(i));
            tickerData.setClosePrice(closePrice.getDouble(i));
            tickerDataList.add(tickerData);
        }
        return null;
    }
}
