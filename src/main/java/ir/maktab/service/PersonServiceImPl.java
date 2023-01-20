package ir.maktab.service;

import ir.maktab.data.model.entity.*;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.PersonRepository;
import ir.maktab.service.interfaces.OrderRegistrationService;
import ir.maktab.service.interfaces.PersonService;
import ir.maktab.util.UtilImage;
import ir.maktab.util.ValidationInput;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PersonServiceImPl implements PersonService {
    private final PersonRepository personRepository = new PersonRepository();
    private final BasicJobsService basicJobsService = BasicJobsService.getInstance();
    private final OrderRegistrationService orderRegistrationService = new OrderRegistrationServiceImpl();

    @Override
    public void save(Person person, File file) throws IOException {
        validateInfoPerson(person);
        if (person instanceof Expert) {
            ((Expert) person).setExpertImage(UtilImage.validateImage(file));
            ((Expert) person).setSpecialtyStatus(SpecialtyStatus.NewState);
            ((Expert) person).setPerformance(0);
        }
        personRepository.save(person);
    }

    public void save(OrderRegistration orderRegistration) {
        orderRegistrationService.saveOrder(orderRegistration);
    }

    @Override
    public Person login(String userName, String password) {
        Person expert = personRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
        if (expert.getPassword().equals(password))
            return expert;
        else
            throw new ValidationException("Your password is incorrect");
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


    @Override
    public void changePassword(String userName, String passwordOld, String newPassword) {
        Person person = login(userName, passwordOld);
        person.setPassword(newPassword);
        update(person);
    }

    @Override
    public Person findByUserName(String userName) {
        return personRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
    }

    @Override
    public void update(Person person) {
        if (Objects.isNull(person))
            throw new NotFoundException("person is not exist");
        personRepository.update(person);
    }

    @Override
    public List<Person> findAllPerson() {
        List<Person> listExpert = personRepository.getAll();
        if (listExpert.isEmpty())
            throw new NotFoundException("there arent experts");
        return listExpert;
    }

    @Override
    public List<Person> findAllExpertsApproved() {
        return findAllPerson().stream().filter(expert -> expert instanceof Expert && ((Expert) expert).
                        getSpecialtyStatus().equals(SpecialtyStatus.Confirmed)).
                collect(Collectors.toList());
    }

    @Override
    public List<Person> findAllExpertsIsNotConfirm() {
        return findAllPerson().stream().filter(expert -> expert instanceof Expert && ((Expert) expert).
                        getSpecialtyStatus().equals(SpecialtyStatus.NewState)).
                collect(Collectors.toList());
    }

    private void validateInfoPerson(Person person) {
        ValidationInput.validateName(person.getFirstName());
        ValidationInput.validateName(person.getLastName());
        ValidationInput.validateEmail(person.getEmail());
        ValidationInput.validateUserName(person.getPassword());
    }
}
