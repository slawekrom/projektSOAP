package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.MovieDao;
import dao.PersonDao;
import dao.ReservationDao;
import dao.ShowingDao;
import db.model.Movie;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;

import javax.jws.WebService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@WebService
public class ReservationServiceImpl implements ReservationService {

    ShowingDao showingDao = new ShowingDao();
    ReservationDao reservationDao = new ReservationDao();
    PersonDao personDao = new PersonDao();
    MovieDao movieDao = new MovieDao();

    public List<Showing> getAllShowings() {
        return showingDao.getAll();
    }

    public void addNewReservation(String places,Boolean isPaid, long personId, long showingId) {
        Person person = personDao.getById(personId);
        Showing showing = showingDao.getById(showingId);
        //TODO Dodawanie miejsc do rezerwacji
        Reservation reservation = new Reservation(places, isPaid, person, showing);
        reservationDao.save(reservation);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(reservationDao.getById(id));
    }

    public void editReservation(long id) {
        reservationDao.update(reservationDao.getById(id));
    }

    public Document getPDFofReservation(long id) throws FileNotFoundException, DocumentException {
        Reservation result = reservationDao.getById(id);
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
        /*chunk = new Chunk("Time of screening: " + result.getShowing().getTime());
        document.add(chunk);*/
        chunk = new Chunk("Places reserved: " + result.getPlaces());
        document.add(chunk);
        document.close();
        return document;
    }

    public List<Reservation> getPersonReservations(long personId) {
       return personDao.getById(personId).getReservations();
    }

    public Movie getMovieInfo(long movieId) {
        return  movieDao.getById(movieId);
    }

    public Movie getMovieInfoByTitle(String title) {
        return movieDao.getByTitle(title);
    }
    public String echo(){
        return "asasasasasAs";
    }
}
