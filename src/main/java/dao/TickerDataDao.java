package dao;

import entities.Ticker;
import entities.TickerData;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


public class TickerDataDao extends AbstractDao {

    public ArrayList<TickerData> getTickerDataOnDate(Ticker t, String d){
        //Date date = Date.valueOf(d);
        LocalDate date = LocalDate.parse(d);
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        System.out.println(d);
        Query q = em.createQuery("SELECT td FROM TickerData td WHERE td.dateEntry.ticker=:ticker" +
                " AND td.dateEntry.date=:date");
        q.setParameter("ticker", t);
        q.setParameter("date", date);
        ArrayList<TickerData> tickerData = (ArrayList<TickerData>) q.getResultList();
        em.getTransaction().commit();
        em.close();
        return tickerData;
    }
}
