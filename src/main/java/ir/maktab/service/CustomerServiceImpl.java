package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.data.model.entity.OrderRegistration;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.data.repository.CustomerRepository;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.service.interfaces.CustomerService;
import ir.maktab.util.ValidationInput;

import java.util.List;
import java.util.Objects;

public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository = CustomerRepository.getInstance();
    OrderRegistrationServiceImpl orderRegistrationService = OrderRegistrationServiceImpl.getInstance();
    BasicJobsService basicJobsService = BasicJobsService.getInstance();

    @Override
    public void save(Customer customer) {
        validateInfoPerson(customer);
        customerRepository.save(customer);
    }

    @Override
    public Customer login(String userName, String password) {
        Customer customer = customerRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("customer not found with this userName"));
        if (customer.getPassword().equals(password))
            return customer;
        else
            throw new ValidationException("Your password is incorrect");
    }

    @Override
    public void changePassword(String userName, String passwordOld, String newPassword) {
        Customer customer = login(userName, passwordOld);
        customer.setPassword(newPassword);
        update(customer);
    }

    @Override
    public Customer findByUserName(String userName) {
        return customerRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("customer not found with this userName"));
    }

    @Override
    public void update(Customer customer) {
        if (Objects.isNull(customer))
            throw new NotFoundException("person is not exist");
        customerRepository.update(customer);
    }

    public void save(OrderRegistration orderRegistration) {
        orderRegistrationService.saveOrder(orderRegistration);
    }

    @Override
    public List<BasicJob> viewAllBasicJobs() {
        List<BasicJob> allBasicJobs = basicJobsService.findAllBasicJobs();
        if (allBasicJobs.isEmpty())
            throw new NotFoundException("there arent basicJob");
        return allBasicJobs;
    }

    @Override
    public List<SubJob> viewSubJobsABasicJob(String nameBasicJob) {
        List<SubJob> allSubJobsABasicJob = basicJobsService.findAllSubJobsABasicJob(nameBasicJob);
        if (allSubJobsABasicJob.isEmpty())
            throw new NotFoundException("there arent subJob for the basicJob");
        return allSubJobsABasicJob;
    }

    private void validateInfoPerson(Customer person) {
        ValidationInput.validateName(person.getFirstName());
        ValidationInput.validateName(person.getLastName());
        ValidationInput.validateEmail(person.getEmail());
        ValidationInput.validateUserName(person.getPassword());
    }
}
