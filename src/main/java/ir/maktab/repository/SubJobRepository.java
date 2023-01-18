package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class SubJobRepository implements InRepository<SubJob, Long> {

    private static SubJobRepository instance = new SubJobRepository();

    public SubJobRepository() {
    }

    public static SubJobRepository getInstance() {
        return instance;
    }

    @Override
    public void save(SubJob subJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(subJob);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<SubJob> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        SubJob subJob;
        try {
            entityManager.getTransaction().begin();
            subJob = entityManager.find(SubJob.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            subJob = null;
        }
        return Optional.ofNullable(subJob);
    }

    @Override
    public List<SubJob> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        List<SubJob> subJobList = (List<SubJob>) entityManager.createQuery("select b from  SubJob b").getResultList();
        entityManager.close();
        return subJobList;
    }

    @Override
    public void update(SubJob subJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(subJob);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(SubJob subJob) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        SubJob servicesDeleted = entityManager.find(SubJob.class, subJob.getId());
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
