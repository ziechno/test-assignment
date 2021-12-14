package dao;

import entities.Data;

import javax.persistence.EntityManager;

public class DataDao extends AbstractDao {

    public DataDao() {
        super();
    }

    public void save(Data data) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(data);
        em.getTransaction().commit();
        em.close();
    }
}