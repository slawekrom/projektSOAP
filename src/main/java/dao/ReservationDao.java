package dao;

import db.model.Reservation;
import db.model.Reservation;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import java.util.List;

public class ReservationDao implements Dao<Reservation>{

    EntityManager entityManager = FactoryHibernate.getEm();

    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }
    public Reservation getById(long id) {
        openTransaction();
        Reservation reservation = entityManager.createQuery("FROM RESERVATION where ID_RES = :id", Reservation.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return reservation;
    }

    public List<Reservation> getAll() {
        openTransaction();
        List<Reservation> reservationList = entityManager.createQuery("FROM RESERVATION", Reservation.class)
                .getResultList();
        commitTransaction();
        return reservationList;
    }
    public List<Reservation> getUserReservation(long id){
        openTransaction();
        List<Reservation> reservationList = entityManager.createQuery("FROM RESERVATION where user.id_person = :id", Reservation.class)
                .setParameter("id", id)
                .getResultList();
        commitTransaction();
        return reservationList;
    }
    public List<Reservation> getReservationByPesel(String pesel){
        openTransaction();
        List<Reservation> reservationList = entityManager.createQuery("FROM RESERVATION where user.pesel = :pesel", Reservation.class)
                .setParameter("pesel", pesel)
                .getResultList();
        commitTransaction();
        return reservationList;
    }
    public List<Reservation> getReservationByName(String firstName, String secondName){
        openTransaction();
        List<Reservation> reservationList = entityManager.createQuery(
                "FROM RESERVATION where user.firstName = :firstName AND user.secondName = :secondName", Reservation.class)
                .setParameter("firstName", firstName).setParameter("secondName", secondName)
                .getResultList();
        commitTransaction();
        return reservationList;
    }

    public void save(Reservation reservation) {
        openTransaction();
        entityManager.persist(reservation);
        commitTransaction();
    }

    public void update(Reservation reservation) {
        openTransaction();
        entityManager.merge(reservation);
        commitTransaction();
    }

    public void delete(Reservation reservation) {
        openTransaction();
        entityManager.remove(reservation);
        commitTransaction();
    }
}
