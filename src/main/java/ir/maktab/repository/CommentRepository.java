package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements InRepository<Review, Long> {

    private static CommentRepository instance = new CommentRepository();

    private CommentRepository() {
    }

    public static CommentRepository getInstance() {
        return instance;
    }

    @Override
    public void save(Review review) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(review);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Review> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Review review;
        try {
            entityManager.getTransaction();
            review = entityManager.find(Review.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            review = null;
        }
        return Optional.ofNullable(review);
    }

    @Override
    public List<Review> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Review> comments = entityManager.createQuery("select c from Review  c").getResultList();
        entityManager.close();
        return comments;
    }

    @Override
    public void update(Review review) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(review);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Review review) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Review reviewDelete = entityManager.find(Review.class, review.getId());
        entityManager.remove(reviewDelete);
        entityManager.close();
    }
}
