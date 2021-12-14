package dao;

import entities.Data;
import entities.Ticker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

public class DataDao extends AbstractDao {

    public DataDao() {
        super();
    }

    public ArrayList<Data> getData(Ticker t, String d){
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT d from Data d WHERE d.date=:date AND d.ticker=:ticker");
        q.setParameter("date", d);
        q.setParameter("ticker", t);
        ArrayList<Data> data = (ArrayList<Data>) q.getResultList();
        em.getTransaction().commit();
        em.close();
        return data;
    }

    public void save(Data data) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }
}