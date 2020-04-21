package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    public List<Showing> getAllShowings() {

        EntityManagerFactory entityManagerFactory = EMF.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("select s from SHOWING s", Showing.class);
        return (List<Showing>) query.getResultList();
    }

    public void addNewReservationToShowing(Showing showing, ArrayList<String> seats) {
//        EntityManagerFactory entityManagerFactory
    }

    public void deleteReservation(Reservation reservation) {

    }

    public void editReservationSeats(Reservation reservation, ArrayList<String> seats) {

    }

    public Document getPDFofReservation(Reservation reservation) throws FileNotFoundException, DocumentException {
        EntityManagerFactory entityManagerFactory = EMF.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        Reservation result = em.find(Reservation.class,reservation.getId_reservation());
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

    }

    public List<Reservation> getPersonReservations(Person person) {
        EntityManagerFactory entityManagerFactory = EMF.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("select r from Reservation r where r.person = :person", Reservation.class)
                .setParameter("person",person);
        return (List<Reservation>) query.getResultList();
    }
}
