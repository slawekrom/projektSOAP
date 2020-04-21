package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    public List<Showing> getAllShowings() {

        EntityManager em = FactoryHibernate.getEm();
        Query query = em.createQuery("select s from SHOWING s", Showing.class);
        return (List<Showing>) query.getResultList();
    }

    public void addNewReservation(Reservation reservation) {
        EntityManager em = FactoryHibernate.getEm();
        em.getTransaction().begin();
        //TODO Dodawanie miejsc do rezerwacji
        Person person = em.find(Person.class, reservation.getPerson().getId_person());
        reservation.setPerson(person);
        Showing showing = em.find(Showing.class,reservation.getShowing().getId_showing());
        reservation.setShowing(showing);
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteReservation(Reservation reservation) {
        EntityManager em = FactoryHibernate.getEm();
        em.getTransaction().begin();
        Reservation reservationToRemove = em.find(Reservation.class, reservation.getId_reservation());
        em.remove(reservationToRemove);
        em.getTransaction().commit();
        em.close();
    }

    public void editReservationSeats(Reservation reservation) {
        EntityManager em = FactoryHibernate.getEm();
        em.getTransaction().begin();
        em.merge(reservation);
        em.getTransaction().commit();
        em.close();
    }

    public Document getPDFofReservation(Reservation reservation) throws FileNotFoundException, DocumentException {
        EntityManager em = FactoryHibernate.getEm();
        Reservation result = em.find(Reservation.class, reservation.getId_reservation());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("reservation.pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Name: " + result.getPerson().getFirstName() + " " + result.getPerson().getSecondName(), font);
        document.add(chunk);
        chunk = new Chunk("Movie title: " + result.getShowing().getMovie().getTitle());
        document.add(chunk);
        chunk = new Chunk("Date of screening: " + result.getShowing().getDate());
        document.add(chunk);
        chunk = new Chunk("Time of screening: " + result.getShowing().getTime());
        document.add(chunk);
        chunk = new Chunk("Places reserved: " + result.getPlaces());
        document.add(chunk);
        document.close();
        return document;

    }

    public List<Reservation> getPersonReservations(Person person) {
        EntityManager em = FactoryHibernate.getEm();
        Query query = em.createQuery("select r from Reservation r where r.person = :person", Reservation.class)
                .setParameter("person", person);
        return (List<Reservation>) query.getResultList();
    }
}
