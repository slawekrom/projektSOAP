package dao;

import db.model.Showing;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ShowingDao implements Dao<Showing>{

    EntityManager entityManager = FactoryHibernate.getEm();

    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }
    public Showing getById(long id) {
        openTransaction();
        Showing showing = entityManager.createQuery("FROM SHOWING where ID_SHOWING = :id", Showing.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return showing;
    }

    public List<Showing> getAll() {
        openTransaction();
        List<Showing> showingList = entityManager.createQuery("FROM SHOWING", Showing.class)
                .getResultList();
        commitTransaction();
        return showingList;
    }
    public List<Showing> getByDate(int year, int month, int day){
        Date date = new GregorianCalendar(year, month-1, day, 0,0,0).getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        Date dateEvening = calendar.getTime();
        System.out.println("d1 " + date.toString() + " and  "+ dateEvening.toString());
        openTransaction();
        List<Showing> showingList = entityManager.createQuery("FROM SHOWING WHERE date BETWEEN :start AND :stop", Showing.class)
                .setParameter("start", date).setParameter("stop", dateEvening).getResultList();
        commitTransaction();
        return showingList;
    }

    public void save(Showing showing) {
        openTransaction();
        entityManager.persist(showing);
        commitTransaction();
    }

    public void update(Showing showing) {
        openTransaction();
        entityManager.merge(showing);
        commitTransaction();
    }

    public void delete(Showing showing) {
        openTransaction();
        entityManager.remove(showing);
        commitTransaction();
    }
}
