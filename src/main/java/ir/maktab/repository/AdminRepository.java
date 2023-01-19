package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Admin;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class AdminRepository {

    public void save(Admin admin) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
            entityManager.persist(admin);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Optional<Admin> findByUserName(String userName) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Admin admin;
        try {
            entityManager.getTransaction();
            admin = (Admin) entityManager.createQuery("select a from Admin a where a.userName=:userName").
                    setParameter("userName", userName).getSingleResult();
            entityManager.close();
        } catch (NoResultException ex) {
            admin = null;
        }
        return Optional.ofNullable(admin);
    }
    public void update(Admin admin) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(admin);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
