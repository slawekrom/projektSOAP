package services;

import com.itextpdf.text.Document;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;

public class ReservationServiceImpl implements ReservationService {

    private static SessionFactory factory;

    ReservationServiceImpl(){
    }

    public ArrayList<Showing> getAllShowings() {
        return null;
    }

    public void addNewReservationToShowing(Showing showing, ArrayList<String> seats) {

    }

    public void deleteReservation(Reservation reservation) {

    }

    public void editReservationSeats(Reservation reservation, ArrayList<String> seats) {

    }

    public Document getPDFofReservation(Reservation reservation) {
        return null;
    }

    public ArrayList<Reservation> getPersonReservations(Person person) {
        return null;
    }
}
