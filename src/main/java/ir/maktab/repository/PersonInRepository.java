package ir.maktab.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class PersonInRepository implements InRepository<Person, Long> {
    private static PersonInRepository instance = new PersonInRepository();

    private PersonInRepository() {
    }

    public static PersonInRepository getInstance() {
        return instance;
    }

    public void save(Person person) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction();
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
        List<Person> personList = entityManager.createQuery("select p from  Person p").getResultList();
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
        Person personDeleted=entityManager.find(Person.class,person.getId());
        entityManager.remove(personDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
