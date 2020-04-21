package hibernate;

import db.model.Movie;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args){

        FactoryHibernate factoryHibernate = new FactoryHibernate();
        EntityManager entityManager = FactoryHibernate.getEm();

        //do testowania połączenia
        List<Movie> result = new ArrayList<Movie>();
        entityManager.getTransaction().begin();
        result = entityManager.createQuery("from MOVIE", Movie.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        if (result.isEmpty()){
            System.out.println("TABLE IS EMPTY");
        }

    }
}
