package dao;

import db.model.User;
import db.model.User;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


public class UserDao implements Dao<User>{

    FactoryHibernate factoryHibernate = new FactoryHibernate();
    EntityManager entityManager = factoryHibernate.getEm();
    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }
    public User getById(long id) {
        openTransaction();
        User user = entityManager.createQuery("FROM USER where ID_USER = :id", User.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return user;
    }

    public User getByPesel(String pesel){
        openTransaction();
        User user = null;
        try {
            user = entityManager.createQuery("FROM USER where pesel = :pesel", User.class)
                    .setParameter("pesel", pesel).getSingleResult();
        }
        catch (NoResultException nre){
        }
        finally {
            commitTransaction();
        }
        return user;
    }
    public User getByFirstAndSecondName(String firstName, String secondName){
        openTransaction();
        User user = entityManager.createQuery("FROM USER where firstName = :firstName AND secondName = :secondName", User.class)
                .setParameter("firstName", firstName)
                .setParameter("secondName",secondName).getSingleResult();
        commitTransaction();
        return user;
    }

    public List<User> getAll() {
        openTransaction();
        List<User> userList = entityManager.createQuery("FROM USER", User.class)
                .getResultList();
        commitTransaction();
        return userList;
    }

    public void save(User user) {
        openTransaction();
        entityManager.persist(user);
        commitTransaction();
    }

    public void update(User user) {
        openTransaction();
        entityManager.merge(user);
        commitTransaction();
    }

    public void delete(User user) {
        openTransaction();
        entityManager.remove(user);
        commitTransaction();
    }
}
