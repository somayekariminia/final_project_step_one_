package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.OrderRegistration;
import ir.maktab.data.model.entity.Services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class OrderRegistrationRepository implements InRepository<OrderRegistration,Long> {
    private static OrderRegistrationRepository instance = new OrderRegistrationRepository();

    private OrderRegistrationRepository() {
    }

    public static OrderRegistrationRepository getInstance() {
        return instance;
    }
    @Override
    public void save(OrderRegistration orderRegistration) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(orderRegistration);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<OrderRegistration> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        OrderRegistration orderRegistration;
        try {
            entityManager.getTransaction();
            orderRegistration = entityManager.find(OrderRegistration.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            orderRegistration = null;
        }
        return Optional.ofNullable(orderRegistration);
    }

    @Override
    public List<OrderRegistration> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<OrderRegistration> registrationList = entityManager.createQuery("select o from  OrderRegistration o").getResultList();
        entityManager.close();
        return registrationList;
    }

    @Override
    public void update(OrderRegistration orderRegistration) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(orderRegistration);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(OrderRegistration orderRegistration) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        OrderRegistration orderRegistration1=entityManager.find(OrderRegistration.class,orderRegistration.getId());
        entityManager.remove(orderRegistration1);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
