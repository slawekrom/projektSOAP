package services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.*;
import db.model.*;

import javax.imageio.ImageIO;
import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.awt.Image;
import java.util.List;

@WebService
@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
@HandlerChain(file="handler-chain.xml")
public class ReservationServiceImpl implements ReservationService {

    ShowingDao showingDao = new ShowingDao();
    ReservationDao reservationDao = new ReservationDao();
    MovieDao movieDao = new MovieDao();
    UserDao userDao = new UserDao();

    @MTOM
    public List<Showing> getAllShowings() {
        return showingDao.getAll();
    }

    public void addNewReservation(@WebParam(name = "places")String places, @WebParam(name = "isPaid")Boolean isPaid,
                                  @WebParam(name = "userId")long userId, @WebParam(name = "showingId")long showingId) {
        User user = userDao.getById(userId);
        Showing showing = showingDao.getById(showingId);
        showing.setOccupiedPlaces(addReservedPlaces(places, showing.getOccupiedPlaces()));
        showing.setFreePlaces(removeFreePlaces(places, showing.getFreePlaces()));
        showingDao.update(showing);
        Reservation reservation = new Reservation(places, isPaid, user, showing);
        reservationDao.save(reservation);
    }

    private String addReservedPlaces(String reservedPlaces, String occupiedPlaces) {
        List<String> placesAsList = new ArrayList<>(Arrays.asList(occupiedPlaces.split(";")));
        placesAsList.addAll(Arrays.asList(reservedPlaces.split(";")));
        return String.join(";", placesAsList);
    }

    private void updateShowingPlaces(String reservedPlaces, Showing showing) {
        List<String> placesAsList = new ArrayList<>(Arrays.asList(showing.getFreePlaces().split(";")));
        placesAsList.addAll(Arrays.asList(reservedPlaces.split(";")));
        showing.setFreePlaces(String.join(";", placesAsList));

        List<String> occupiedPlacesAsList = new ArrayList<>(Arrays.asList(showing.getOccupiedPlaces().split(";")));
        occupiedPlacesAsList.removeAll(Arrays.asList(reservedPlaces.split(";")));
        showing.setOccupiedPlaces(String.join(";", occupiedPlacesAsList));
        showingDao.update(showing);
    }

    private String removeFreePlaces(String reservedPlaces, String freePlaces) {
        List<String> freePlacesAsList = new ArrayList<>(Arrays.asList(freePlaces.split(";")));
        String[] reservedPlacesAsArray = reservedPlaces.split(";");
        for (String place : reservedPlacesAsArray) {
            freePlacesAsList.remove(place);
        }
        return String.join(";", freePlacesAsList);
    }

    public boolean ifPlacesFree(@WebParam(name = "places")String places, @WebParam(name = "showingId")long showingId) {
        Showing showing = showingDao.getById(showingId);
        List<String> freePlaces = new ArrayList<>(Arrays.asList(showing.getFreePlaces().split(";")));
        String[] reservedPlaces = places.split(";");
        for (String place : reservedPlaces) {
            if (!freePlaces.contains(place))
                return false;
        }
        return true;
    }

    @Override
    public List<Reservation> getUserReservationsByPesel(@WebParam(name = "pesel")String pesel) {
        return reservationDao.getReservationByPesel(pesel);
    }

    @Override
    public List<Reservation> getUserReservationsByName(@WebParam(name = "firstName")String firstName,
                                                       @WebParam(name = "secondName")String secondName) {
        return reservationDao.getReservationByName(firstName, secondName);
    }

    @Override
    public User getUserByPesel(@WebParam(name = "pesel")String pesel) {
        return userDao.getByPesel(pesel);
    }

    @Override
    public void addUser(@WebParam(name = "firstName")String firstName, @WebParam(name = "secondName")String secondName,
                        @WebParam(name = "pesel")String pesel,@WebParam(name = "password") String password) {

        User user = new User(firstName, secondName, pesel, encode(password));
        userDao.save(user);
    }
    private String encode(String password){
        System.out.println("password encoded: " + Base64.getEncoder().encodeToString(password.getBytes()));
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    private String decode(String password){
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedPassword = new String(decodedBytes);
        return decodedPassword;
    }

    @Override
    public boolean checkIfUserExist(@WebParam(name = "pesel")String pesel) {
        return userDao.getByPesel(pesel) != null;
    }

    @Override
    @MTOM
    public List<Showing> getShowingsByDate(@WebParam(name = "year")int year, @WebParam(name = "month")int month,
                                           @WebParam(name = "day")int day) {
        return showingDao.getByDate(year, month, day);
    }

    @Override
    public boolean authorize(@WebParam(name = "pesel")String pesel, @WebParam(name = "password")String password) {
        User user = userDao.getByPesel(pesel);
        if (user == null)
            return false;
        else return password.equals(decode(user.getPassword()));
    }

    @Override
    @MTOM
    public Image getImage(@WebParam(name = "movieId")long id) {
        Image image = null;
        ImageDao imageDao = new ImageDao();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageDao.getByMovieId(id).getImage());
        try {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void deleteReservation(@WebParam(name = "reservationId")long id) {
        Reservation reservation = reservationDao.getById(id);
        Showing showing = reservation.getShowing();
        updateShowingPlaces(reservation.getPlaces(), showing);
        reservationDao.delete(reservationDao.getById(id));
    }

    public void editReservation(@WebParam(name = "reservationId")long id, @WebParam(name = "places")String newPlaces,
                                @WebParam(name = "isPaid")boolean isPaid) {
        Reservation reservation = reservationDao.getById(id);
        Showing showing = reservation.getShowing();
        updateShowingPlaces(reservation.getPlaces(), showing);
        showing.setOccupiedPlaces(addReservedPlaces(newPlaces, showing.getOccupiedPlaces()));
        showing.setFreePlaces(removeFreePlaces(newPlaces, showing.getFreePlaces()));
        showingDao.update(showing);
        reservation.setIsPaid(isPaid);
        reservation.setPlaces(newPlaces);
        reservationDao.update(reservation);
    }

    @MTOM
    public byte[] getPDFofReservation(@WebParam(name = "reservationId")long id) throws IOException, DocumentException {
        Reservation result = reservationDao.getById(id);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("resources/" + id + "_reservation.pdf"));
        document.open();
        Chunk chunk = new Chunk("Person name and surname: " + result.getUser().getFirstName() + " " + result.getUser().getSecondName());
        document.add(chunk);
        document.add(new Paragraph());
        chunk = new Chunk("Movie title: " + result.getShowing().getMovie().getTitle());
        document.add(chunk);
        document.add(new Paragraph());
        chunk = new Chunk("Date of screening: " + result.getShowing().getDate());
        document.add(chunk);
        document.add(new Paragraph());
        chunk = new Chunk("Places reserved: " + result.getPlaces());
        document.add(chunk);
        document.add(new Paragraph());
        document.close();
        File f = new File("resources/" + id + "_reservation.pdf");
        return Files.readAllBytes(f.toPath());
    }

    public List<Reservation> getUserReservations(@WebParam(name = "userId")long userId) {
        return reservationDao.getUserReservation(userId);
    }

    @MTOM
    public Movie getMovieInfo(@WebParam(name = "movieId")long movieId) {
        return movieDao.getById(movieId);
    }

    @MTOM
    public Movie getMovieInfoByTitle(@WebParam(name = "title")String title) {
        return movieDao.getByTitle(title);
    }
}
