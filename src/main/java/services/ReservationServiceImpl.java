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
import javax.persistence.NoResultException;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebService
@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class ReservationServiceImpl implements ReservationService {

    ShowingDao showingDao = new ShowingDao();
    ReservationDao reservationDao = new ReservationDao();
    PersonDao personDao = new PersonDao();
    MovieDao movieDao = new MovieDao();

    @MTOM
    public List<Showing> getAllShowings() {
        return showingDao.getAll();
    }

    public void addNewReservation(String places,Boolean isPaid, long personId, long showingId) {
        Person person = personDao.getById(personId);
        Showing showing = showingDao.getById(showingId);
        showing.setOccupiedPlaces(addReservedPlaces(places, showing.getOccupiedPlaces()));
        showing.setFreePlaces(removeFreePlaces(places, showing.getFreePlaces()));
        showingDao.update(showing);
        Reservation reservation = new Reservation(places, isPaid, person, showing);
        reservationDao.save(reservation);
    }
    private String addReservedPlaces(String reservedPlaces, String occupiedPlaces){
        List<String> placesAsList = new ArrayList<>(Arrays.asList(occupiedPlaces.split(";")));
        placesAsList.addAll(Arrays.asList(reservedPlaces.split(";")));
        return String.join(";",placesAsList);
    }
    private void updateShowingPlaces(String reservedPlaces, long showingId){
        Showing showing = showingDao.getById(showingId);
        List<String> placesAsList = new ArrayList<>(Arrays.asList(showing.getFreePlaces().split(";")));
        placesAsList.addAll(Arrays.asList(reservedPlaces.split(";")));
        showing.setFreePlaces(String.join(";",placesAsList));

        List<String> occupiedPlacesAsList = new ArrayList<>(Arrays.asList(showing.getOccupiedPlaces().split(";")));
        occupiedPlacesAsList.removeAll(Arrays.asList(reservedPlaces.split(";")));
        showing.setOccupiedPlaces(String.join(";",occupiedPlacesAsList));
        showingDao.update(showing);
    }

    private String removeFreePlaces(String reservedPlaces, String freePlaces){
        List<String> freePlacesAsList = new ArrayList<>(Arrays.asList(freePlaces.split(";")));
        String[] reservedPlacesAsArray = reservedPlaces.split(";");
        for(String place : reservedPlacesAsArray){
            freePlacesAsList.remove(place);
        }
        return String.join(";",freePlacesAsList);
    }
    public boolean ifPlacesFree(String places, long showingId){
        Showing showing = showingDao.getById(showingId);
        List<String> freePlaces = new ArrayList<>(Arrays.asList(showing.getFreePlaces().split(";")));
        String[] reservedPlaces = places.split(";");
        for (String place : reservedPlaces){
            if (!freePlaces.contains(place))
                return false;
        }
        return true;
    }

    @Override
    public List<Reservation> getPersonReservationsByPesel(String pesel) {
        return reservationDao.getReservationByPesel(pesel);
    }

    @Override
    public List<Reservation> getPersonReservationsByName(String firstName, String secondName) {
        return reservationDao.getReservationByName(firstName, secondName);
    }

    @Override
    public Person getPersonByPesel(String pesel) {
        return personDao.getByPesel(pesel);
    }

    @Override
    public void addPerson(String firstName, String secondName, String pesel) {
        Person person = new Person(firstName, secondName, pesel);
        personDao.save(person);
    }

    @Override
    public boolean checkIfPersonExist(String pesel) {
        return personDao.getByPesel(pesel) != null;
    }

    @Override
    @MTOM
    public List<Showing> getShowingsByDate(Date date) {
        return showingDao.getByDate(date);
    }


    public void deleteReservation(long id) {
        Reservation reservation = reservationDao.getById(id);
        updateShowingPlaces(reservation.getPlaces(), reservation.getShowing().getId_showing());
        reservationDao.delete(reservationDao.getById(id));
    }

    public void editReservation(long id) {
        reservationDao.update(reservationDao.getById(id));
    }

    @MTOM
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
       return reservationDao.getPersonReservation(personId);
    }

    @MTOM
    public Movie getMovieInfo(long movieId) {
        return  movieDao.getById(movieId);
    }

    @MTOM
    public Movie getMovieInfoByTitle(String title) {
        return movieDao.getByTitle(title);
    }
}
