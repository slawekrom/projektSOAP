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
