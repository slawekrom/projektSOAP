package hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class FactoryHibernate {
    private static EntityManager em;

    public FactoryHibernate(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tickets");
        em = emf.createEntityManager();
    }

    public static EntityManager getEm() {
        return em;
    }
}
