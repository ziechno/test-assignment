package dao;

import entities.Ticker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class TickerDao extends AbstractDao {

    public TickerDao(){
        super();
    }

    public Ticker getTicker(){
        return null;
    }

    public List<Ticker> getAllTickers() {
        EntityManager em = createEntityManager();
        Query q = em.createQuery("SELECT t FROM Ticker t");
        List<Ticker> resultList = q.getResultList();
        em.close();
        return resultList;
    }

    public Ticker findBySymbol(String s) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT t FROM Ticker t WHERE t.symbol= :symbol").setParameter("symbol",s);
        Ticker ticker = null;

        if(!(q.getResultList().isEmpty())){
            System.out.println("not empty");
            ticker = (Ticker) q.getResultList().get(0);
        }
        em.getTransaction().commit();
        em.close();
        return ticker;
    }

    public void save(Ticker t) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    public ArrayList<Ticker> getAllFromList(ArrayList<String> tickerSymbols) {
        ArrayList<Ticker> tickers;
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT t FROM Ticker t WHERE t.symbol IN :tickerSymbols");
        q.setParameter("tickerSymbols", tickerSymbols);
        tickers = (ArrayList<Ticker>) q.getResultList();
        em.getTransaction().commit();
        em.close();
        return tickers;
    }
}
