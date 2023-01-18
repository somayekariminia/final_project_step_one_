package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.PersonRepository;
import ir.maktab.util.ValidationInput;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class PersonServiceImPl implements PersonService {
    private final PersonRepository personRepository = new PersonRepository();

    public void save(Person person, File file) {
        validateInfoPerson(person);
        if (person instanceof Expert) {
            try {
                ((Expert) person).setExpertImage(ValidationInput.validateImage(file));
                ((Expert) person).setSpecialtyStatus(SpecialtyStatus.NewState);
            } catch (IOException | ValidationException e) {
                System.err.println(e.getMessage());
            }
        }
        personRepository.save(person);
    }

    public Person login(String userName, String password) {
        Person expert = personRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
        if (expert.getPassword().equals(password))
            return expert;
        else
            throw new ValidationException("Your password is incorrect");
    }

    public void changePassword(String userName, String passwordOld, String newPassword) {
        Person person = login(userName, passwordOld);
        person.setPassword(newPassword);
        update(person);
    }


    public Person findByUserName(String userName) {
        return personRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
    }

    @Override
    public void update(Person person) {
        personRepository.update(person);
    }

    public List<Person> findAllPerson() {
        List<Person> listExpert = personRepository.getAll();
        if (listExpert.isEmpty())
            throw new NotFoundException("List Experts Are empty");
        return listExpert;
    }

    public List<Person> findAllExpertsIsNotConfirm() {
        return findAllPerson().stream().filter(expert -> expert instanceof Expert && ((Expert) expert).getSpecialtyStatus().equals(SpecialtyStatus.NewState)).collect(Collectors.toList());
    }

    private void validateInfoPerson(Person person) {

        try {
            ValidationInput.validateName(person.getFirstName());
            ValidationInput.validateName(person.getLastName());
            ValidationInput.validateEmail(person.getEmail());
            ValidationInput.validateUserName(person.getPassword());
        } catch (ValidationException e) {
            System.err.println(e.getMessage());
        }

    }
}
