package dao;

import entities.Ticker;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query q = em.createQuery("SELECT t FROM Ticker t WHERE t.symbol= :symbol").setParameter("symbol",s);
        if(q.getResultList().isEmpty()){
            em.close();
            System.out.println("empty");
            return null;
        }
        Ticker ticker = (Ticker) q.getResultList().get(0);
        System.out.println(ticker.getSymbol() + " from DAO");
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
}
