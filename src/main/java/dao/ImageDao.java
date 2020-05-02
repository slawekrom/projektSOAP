package dao;

import db.model.Image;
import hibernate.FactoryHibernate;

import javax.persistence.EntityManager;
import java.util.List;

public class ImageDao  implements Dao<Image>{
    EntityManager entityManager = FactoryHibernate.getEm();

    private void openTransaction(){
        entityManager.getTransaction().begin();
    }
    private void commitTransaction(){
        entityManager.getTransaction().commit();
    }

    public Image getById(long id) {
        openTransaction();
        Image image = entityManager.createQuery("FROM IMAGE where ID_IMAGE = :id", Image.class)
                .setParameter("id", id).getSingleResult();
        commitTransaction();
        return image;
    }
    public Image getByMovieId(long movieId){
        openTransaction();
        Image image = entityManager.createQuery("FROM IMAGE where ID_MOVIE = :id", Image.class)
                .setParameter("id", movieId).getSingleResult();
        commitTransaction();
        return image;
    }

    public List<Image> getAll() {
        openTransaction();
        List<Image> imageList = entityManager.createQuery("FROM IMAGE", Image.class)
                .getResultList();
        commitTransaction();
        return imageList;
    }

    public void save(Image image) {
        openTransaction();
        entityManager.persist(image);
        commitTransaction();
    }

    public void update(Image image) {
        openTransaction();
        entityManager.merge(image);
        commitTransaction();
    }

    public void delete(Image image) {
        openTransaction();
        entityManager.remove(image);
        commitTransaction();
    }
}
