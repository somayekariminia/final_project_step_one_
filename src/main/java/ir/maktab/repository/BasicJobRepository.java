package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class BasicJobRepository implements InRepository<BasicJob, Long> {

    private static BasicJobRepository instance = new BasicJobRepository();

    public BasicJobRepository() {
    }

    public static BasicJobRepository getInstance() {
        return instance;
    }

    @Override
    public void save(BasicJob basicJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(basicJob);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<BasicJob> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        BasicJob basicJob;
        try {
            entityManager.getTransaction().begin();
            basicJob = entityManager.find(BasicJob.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            basicJob = null;
        }
        return Optional.ofNullable(basicJob);
    }

    @Override
    public List<BasicJob> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        List<BasicJob> servicesList = entityManager.createQuery("select b from  BasicJob b").getResultList();
        entityManager.close();
        return servicesList;
    }

    @Override
    public void update(BasicJob basicJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(basicJob);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(BasicJob basicJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        BasicJob servicesDeleted = entityManager.find(SubJob.class, basicJob.getId());
        entityManager.remove(servicesDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<SubJob> getByName(String name) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        SubJob subJob;
        try {
            entityManager.getTransaction().begin();
            subJob = (SubJob) entityManager.createQuery("select s from SubJob s where s.subJobName=:subJobName").
                    setParameter("subJobName", name).getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            subJob = null;
        }
        return Optional.ofNullable(subJob);
    }
}
