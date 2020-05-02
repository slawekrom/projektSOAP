package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import db.model.Movie;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ReservationService {
    @WebMethod
    List<Showing> getAllShowings();
    @WebMethod
    void addNewReservation(String places,Boolean isPaid, long personId, long showingId);
    @WebMethod
    void deleteReservation(long id);
    @WebMethod
    void editReservation(long id);
    @WebMethod
    Document getPDFofReservation(long id) throws FileNotFoundException, DocumentException;
    @WebMethod
    List<Reservation> getPersonReservations(long personId);
    @WebMethod
    Movie getMovieInfo(long movieId);
    @WebMethod
    Movie getMovieInfoByTitle(String title);
    @WebMethod
    public boolean ifPlacesFree(String places, long showingId);
    @WebMethod
    public List<Reservation> getPersonReservationsByPesel(String pesel);
    @WebMethod
    public List<Reservation> getPersonReservationsByName(String firstName, String secondName);
    @WebMethod
    Person getPersonByPesel(String pesel);
    @WebMethod
    public void addPerson(String firstName, String secondName, String pesel);
    @WebMethod
    public boolean checkIfPersonExist(String pesel);
    @WebMethod
    public List<Showing> getShowingsByDate(int year, int month, int day);
    @WebMethod
    public Image getImage(long id);
}
