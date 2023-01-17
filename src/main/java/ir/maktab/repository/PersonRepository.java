package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class PersonRepository implements InRepository<Person,Long>{
    @Override
    public void save(Person person) {
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
    public Optional<Person> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Person person;
        try {
            entityManager.getTransaction();
            person = entityManager.find(Person.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            person = null;
        }
        return Optional.ofNullable(person);
    }

    @Override
    public List<Person> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Person> personList = entityManager.createQuery("select e from  Person e").getResultList();
        entityManager.close();
        return personList;
    }

    @Override
    public void update(Person person) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Person person) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Person personDeleted = entityManager.find(Person.class, person.getId());
        entityManager.remove(personDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public Optional<Person> findByUserName(String userName) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Person expert;
        try {
            entityManager.getTransaction();
            expert = (Expert) entityManager.createQuery("select e from Person e where e.email=:email").
                    setParameter("email", userName).getSingleResult();
            entityManager.close();
        } catch (NoResultException ex) {
            expert = null;
        }
        return Optional.ofNullable(expert);
    }
}
