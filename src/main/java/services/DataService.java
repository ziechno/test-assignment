package services;

import dao.DataDao;
import entities.Data;
import entities.Ticker;

import java.util.ArrayList;

public class DataService {
    private DataDao dataDao;

    public ArrayList<Data> fetchData(Ticker t, String d){
        dataDao = new DataDao();
        ArrayList<Data> data = dataDao.getData(t, d);
        return data;
    }
}

