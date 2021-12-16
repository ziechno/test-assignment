package dao;


import entities.DataEntry;
import entities.Ticker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataEntryDao extends AbstractDao {

    public DataEntryDao() {
        super();
    }

    public ArrayList<DataEntry> getDailyData(Ticker t, String date) {
        LocalDateTime start = LocalDate.parse(date).atStartOfDay();
        LocalDateTime end = start.plusHours(24);
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT de FROM DataEntry de WHERE de.ticker=:ticker AND " +
                "de.timestamp BETWEEN :start AND :end");
        q.setParameter("ticker", t);
        q.setParameter("start", start);
        q.setParameter("end", end);

        ArrayList<DataEntry> listOfData = (ArrayList<DataEntry>) q.getResultList();
        return listOfData;
    }

    public void saveAll(ArrayList<DataEntry> tickerData) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        for(DataEntry dt : tickerData){
            em.persist(dt);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void save(DataEntry data) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }
}