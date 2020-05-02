package dao;

import db.model.Person;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class PersonDao implements Dao<Person>{

    FactoryHibernate factoryHibernate = new FactoryHibernate();
    EntityManager entityManager = factoryHibernate.getEm();

    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }
    public Person getById(long id) {
        openTransaction();
        Person person = entityManager.createQuery("FROM PERSON where ID_PERSON = :id", Person.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return person;
    }

    public Person getByPesel(String pesel){
        openTransaction();
        Person person = null;
        try {
            person = entityManager.createQuery("FROM PERSON where pesel = :pesel", Person.class)
                    .setParameter("pesel", pesel).getSingleResult();
        }
        catch (NoResultException nre){
        }
        finally {
            commitTransaction();
        }
        return person;
    }
    public Person getByFirstAndSecondName(String firstName, String secondName){
        openTransaction();
        Person person = entityManager.createQuery("FROM PERSON where firstName = :firstName AND secondName = :secondName", Person.class)
                .setParameter("firstName", firstName)
                .setParameter("secondName",secondName).getSingleResult();
        commitTransaction();
        return person;
    }

    public List<Person> getAll() {
        openTransaction();
        List<Person> personList = entityManager.createQuery("FROM PERSON", Person.class)
                .getResultList();
        commitTransaction();
        return personList;
    }

    public void save(Person person) {
        openTransaction();
        entityManager.persist(person);
        commitTransaction();
    }

    public void update(Person person) {
        openTransaction();
        entityManager.merge(person);
        commitTransaction();
    }

    public void delete(Person person) {
        openTransaction();
        entityManager.remove(person);
        commitTransaction();
    }
}
