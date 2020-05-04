package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import db.model.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ReservationService {
    @WebMethod
    List<Showing> getAllShowings();
    @WebMethod
    void addNewReservation(String places,Boolean isPaid, long userId, long showingId);
    @WebMethod
    void deleteReservation(long id);
    @WebMethod
    void editReservation(long id, String newPlaces, boolean isPaid);
    @WebMethod
    byte[] getPDFofReservation(long id) throws IOException, DocumentException;
    @WebMethod
    List<Reservation> getUserReservations(long userId);
    @WebMethod
    Movie getMovieInfo(long movieId);
    @WebMethod
    Movie getMovieInfoByTitle(String title);
    @WebMethod
    public boolean ifPlacesFree(String places, long showingId);
    @WebMethod
    public List<Reservation> getUserReservationsByPesel(String pesel);
    @WebMethod
    public List<Reservation> getUserReservationsByName(String firstName, String secondName);
    @WebMethod
    User getUserByPesel(String pesel);
    @WebMethod
    public void addUser(String firstName, String secondName, String pesel, String password);
    @WebMethod
    public boolean checkIfUserExist(String pesel);
    @WebMethod
    public List<Showing> getShowingsByDate(int year, int month, int day);
    @WebMethod
    public boolean authorize(String pesel, String password);
    @WebMethod
    public Image getImage(long id);
}
