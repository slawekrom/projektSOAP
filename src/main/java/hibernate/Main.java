package hibernate;

import dao.MovieDao;
import dao.PersonDao;
import db.model.Movie;
import db.model.Person;
import db.model.Reservation;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args){

        FactoryHibernate factoryHibernate = new FactoryHibernate();
        EntityManager entityManager = factoryHibernate.getEm();

        //do testowania połączenia
        /*List<Movie> result = new ArrayList<Movie>();
        entityManager.getTransaction().begin();
        result = entityManager.createQuery("from MOVIE", Movie.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        if (result.isEmpty()){
            System.out.println("TABLE IS EMPTY");
        }
        PersonDao personDao = new PersonDao();
        Person p = new Person("fName", "sName","78123698745");
        *//*personDao.save(p);*//**//*
        MovieDao movieDao = new MovieDao();
        *//**//*Movie m = new Movie("Film1", "dwdwDw", null, p);
        movieDao.save(m);*//*
        Movie m = movieDao.getById(1);
        m.getActors().add(personDao.getById(1));
        m.getActors().add(personDao.getById(2));
        movieDao.save(m);*/

    }
}
