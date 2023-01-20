package ir.maktab.data.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.repository.interfaces.InRepository;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class ExpertRepository implements InRepository<Expert,Long> {
    private static ExpertRepository instance = new ExpertRepository();

    public ExpertRepository() {
    }

    public static ExpertRepository getInstance() {
        return instance;
    }

    @Override
    public void save(Expert person) {
       EntityManager entityManager=ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Expert> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Expert person;
        try {
            entityManager.getTransaction();
            person = entityManager.find(Expert.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            person = null;
        }
        return Optional.ofNullable(person);
    }

    @Override
    public List<Expert> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Expert> expertList = entityManager.createQuery("select e from  Expert e").getResultList();
        entityManager.close();
        return expertList;
    }

    @Override
    public void update(Expert person) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Expert person) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Person personDeleted = entityManager.find(Person.class, person.getId());
        entityManager.remove(personDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public Optional<Expert> findByUserName(String userName) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Expert expert;
        try {
            entityManager.getTransaction();
            expert = (Expert) entityManager.createQuery("select e from Expert e where e.email=:email").
                    setParameter("email", userName).getSingleResult();
            entityManager.close();
        } catch (NoResultException ex) {
            expert = null;
        }
        return Optional.ofNullable(expert);
    }
}
