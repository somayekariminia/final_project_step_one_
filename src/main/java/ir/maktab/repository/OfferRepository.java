package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Offers;
import ir.maktab.data.model.entity.OrderRegistration;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class OfferRepository implements InRepository<Offers, Long> {
    private static OfferRepository instance = new OfferRepository();

    private OfferRepository() {
    }

    public static OfferRepository getInstance() {
        return instance;
    }

    @Override
    public void save(Offers offers) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(offers);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Offers> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Offers offers;
        try {
            entityManager.getTransaction();
            offers = entityManager.find(Offers.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            offers = null;
        }
        return Optional.ofNullable(offers);
    }

    @Override
    public List<Offers> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Offers> offers = entityManager.createQuery("select o from  Offers o").getResultList();
        entityManager.close();
        return offers;
    }

    @Override
    public void update(Offers offers) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(offers);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Offers offers) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Offers offerDelete=entityManager.find(Offers.class,offers.getId());
        entityManager.remove(offerDelete);
        entityManager.close();
    }
}
