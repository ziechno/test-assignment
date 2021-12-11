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

    public void save(Ticker ticker) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(ticker);
        em.getTransaction().commit();
        em.close();
    }

}
