package hibernate;

import dao.MovieDao;
import dao.PersonDao;
import dao.ReservationDao;
import dao.ShowingDao;
import db.model.Movie;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;
import services.ReservationServiceImpl;


import javax.persistence.EntityManager;
import javax.xml.ws.Endpoint;
import java.sql.Time;
import java.util.*;

public class Main {
    public static void main(String [] args){

       /* FactoryHibernate factoryHibernate = new FactoryHibernate();
        EntityManager entityManager = factoryHibernate.getEm();

        //do testowania połączenia

        PersonDao personDao = new PersonDao();
        Person p = personDao.getById(1);
        MovieDao movieDao = new MovieDao();
        Movie m = movieDao.getById(1);
        ShowingDao showingDao = new ShowingDao();
        Date d = new GregorianCalendar(2020, Calendar.APRIL, 15).getTime();

        Time t =new Time(16,0,0);
        Showing s = new Showing(d,t,"1;2;3;4;5","7;8",m);
        showingDao.save(s);
        ReservationDao reservationDao = new ReservationDao();
        Reservation r = new Reservation("1;2",true,p,s);
        reservationDao.save(r);*/
        Endpoint.publish("http://localhost:8080/projekt", new ReservationServiceImpl());
        System.out.println("---------------------------");


    }
}
