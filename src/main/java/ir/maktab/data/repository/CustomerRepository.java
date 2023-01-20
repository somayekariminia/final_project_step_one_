package ir.maktab.data.repository;

import ir.maktab.Config.ConfigJpa;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.repository.interfaces.InRepository;
import ir.maktab.service.BasicJobsService;
import ir.maktab.service.SubJobServiceImpl;
import ir.maktab.service.interfaces.BasicJobService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class CustomerRepository implements InRepository<Customer,Long> {
    private static CustomerRepository instance = new CustomerRepository();

    public CustomerRepository() {
    }

    public static CustomerRepository getInstance() {
        return instance;
    }
    @Override
    public void save(Customer customer) {
        EntityManager entityManager= ConfigJpa.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<Customer> getById(Long aLong) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Customer person;
        try {
            entityManager.getTransaction();
            person = entityManager.find(Customer.class, aLong);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (NoResultException ex) {
            person = null;
        }
        return Optional.ofNullable(person);
    }

    @Override
    public List<Customer> getAll() {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        List<Customer> personList = entityManager.createQuery("select e from  Customer e").getResultList();
        entityManager.close();
        return personList;
    }

    @Override
    public void update(Customer customer) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Customer customer) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        entityManager.getTransaction();
        Customer personDeleted = entityManager.find(Customer.class, customer.getId());
        entityManager.remove(personDeleted);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public Optional<Customer> findByUserName(String userName) {
        EntityManager entityManager = ConfigJpa.getInstance().createEntityManager();
        Customer expert;
        try {
            entityManager.getTransaction();
            expert = (Customer) entityManager.createQuery("select e from Customer e where e.email=:email").
                    setParameter("email", userName).getSingleResult();
            entityManager.close();
        } catch (NoResultException ex) {
            expert = null;
        }
        return Optional.ofNullable(expert);
    }
}
