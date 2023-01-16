package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Comments;
import ir.maktab.data.model.entity.Offers;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements InRepository<Comments,Long> {

    private static CommentRepository instance = new CommentRepository();

    private CommentRepository() {
    }

    public static CommentRepository getInstance() {
        return instance;
    }

    @Override
    public void save(Comments comments) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(comments);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Comments> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Comments comments;
        try {
            entityManager.getTransaction();
            comments = entityManager.find(Comments.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            comments = null;
        }
        return Optional.ofNullable(comments);
    }

    @Override
    public List<Comments> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Comments> comments = entityManager.createQuery("select c from Comments  c").getResultList();
        entityManager.close();
        return comments;
    }

    @Override
    public void update(Comments comments) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(comments);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Comments comments) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Comments commentsDelete=entityManager.find(Comments.class,comments.getId());
        entityManager.remove(commentsDelete);
        entityManager.close();
    }
}
