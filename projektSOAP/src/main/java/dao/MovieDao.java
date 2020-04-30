package dao;

import db.model.Movie;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import java.util.List;

public class MovieDao implements Dao<Movie> {

    EntityManager entityManager = FactoryHibernate.getEm();

    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }

    public Movie getById(long id) {
        openTransaction();
        Movie movie = entityManager.createQuery("FROM MOVIE where ID_MOVIE = :id", Movie.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return movie;
    }
    public Movie getByTitle(String title) {
        openTransaction();
        Movie movie = entityManager.createQuery("FROM MOVIE where title = :title", Movie.class)
                .setParameter("title", title).getSingleResult();
        commitTransaction();
        return movie;
    }

    public List<Movie> getAll() {
        openTransaction();
        List<Movie> movieList = entityManager.createQuery("FROM MOVIE", Movie.class)
                .getResultList();
        commitTransaction();
        return movieList;
    }

    public void save(Movie movie) {
        openTransaction();
        entityManager.persist(movie);
        commitTransaction();
    }

    public void update(Movie movie) {
        openTransaction();
        entityManager.merge(movie);
        commitTransaction();
    }

    public void delete(Movie movie) {
        openTransaction();
        entityManager.remove(movie);
        commitTransaction();
    }
}
