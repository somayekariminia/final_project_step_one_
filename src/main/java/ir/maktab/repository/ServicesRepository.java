package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ServicesRepository implements InRepository<Services, Long> {

    private static ServicesRepository instance = new ServicesRepository();

    private ServicesRepository() {
    }

    public static ServicesRepository getInstance() {
        return instance;
    }

    @Override
    public void save(Services services) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(services);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Services> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Services services;
        try {
            entityManager.getTransaction();
            services = entityManager.find(Services.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            services = null;
        }
        return Optional.ofNullable(services);
    }

    @Override
    public List<Services> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Services> servicesList = entityManager.createQuery("select s from  Services s").getResultList();
        entityManager.close();
        return servicesList;
    }

    @Override
    public void update(Services services) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(services);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Services services) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Services servicesDeleted=entityManager.find(Services.class,services.getId());
        entityManager.remove(servicesDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
