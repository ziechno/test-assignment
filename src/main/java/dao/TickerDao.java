package dao;

import entities.Ticker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

public class TickerDao extends AbstractDao {

    public TickerDao() {
        super();
    }

    public void save(Ticker t) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    public ArrayList<Ticker> getAllFromList(ArrayList<String> tickerSymbols) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery("SELECT t FROM Ticker t WHERE t.symbol IN :tickerSymbols");
        q.setParameter("tickerSymbols", tickerSymbols);
        ArrayList<Ticker> tickers = (ArrayList<Ticker>) q.getResultList();

        em.getTransaction().commit();
        em.close();
        return tickers;
    }

    public void saveAll(ArrayList<Ticker> tickerList) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();

        for(Ticker t : tickerList){
            em.persist(t);
        }

        em.getTransaction().commit();
        em.close();
    }
}
