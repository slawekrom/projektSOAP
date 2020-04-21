package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import db.model.Person;
import db.model.Reservation;
import db.model.Showing;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.FileNotFoundException;
import java.util.List;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ReservationService {
    @WebMethod
    List<Showing> getAllShowings();
    @WebMethod
    void addNewReservation(Reservation reservation);
    @WebMethod
    void deleteReservation(Reservation reservation);
    @WebMethod
    void editReservationSeats(Reservation reservation);
    @WebMethod
    Document getPDFofReservation(Reservation reservation) throws FileNotFoundException, DocumentException;
    @WebMethod
    List<Reservation> getPersonReservations(Person person);
}
