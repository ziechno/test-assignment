package services;

import dao.DataDao;
import entities.Ticker;
import entities.TickerData;

import java.util.ArrayList;

public class DataService {
    private DataDao dataDao;

    public ArrayList<TickerData> fetchData(Ticker t, String d){
        dataDao = new DataDao();
        ArrayList<TickerData> data = dataDao.getData(t, d);
        return data;
    }
}

