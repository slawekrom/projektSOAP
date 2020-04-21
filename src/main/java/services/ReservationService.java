package services;

import com.itextpdf.text.Document;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ReservationService {
    @WebMethod
    ArrayList<Showing> getAllShowings();
    @WebMethod
    void addNewReservationToShowing(Showing showing, ArrayList<String> seats);
    @WebMethod
    void deleteReservation(Reservation reservation);
    @WebMethod
    void editReservationSeats(Reservation reservation, ArrayList<String> seats);
    @WebMethod
    Document getPDFofReservation(Reservation reservation);
    @WebMethod
    ArrayList<Reservation> getPersonReservations(Person person);
}
