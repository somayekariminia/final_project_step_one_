package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.PersonRepository;
import ir.maktab.service.interfaces.PersonService;
import ir.maktab.util.ValidationInput;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PersonServiceImPl implements PersonService {
    private final PersonRepository personRepository = new PersonRepository();

    @Override
    public void save(Person person, File file) throws IOException {
        validateInfoPerson(person);
        if (person instanceof Expert) {
            ((Expert) person).setExpertImage(ValidationInput.validateImage(file));
            ((Expert) person).setSpecialtyStatus(SpecialtyStatus.NewState);
            ((Expert) person).setRating(0);
        }
        personRepository.save(person);
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
            throw new NotFoundException("List Experts Are empty");
        return listExpert;
    }

    @Override
    public List<Person> findAllExpertsIsNotConfirm() {
        return findAllPerson().stream().filter(expert -> expert instanceof Expert && ((Expert) expert).getSpecialtyStatus().equals(SpecialtyStatus.NewState)).collect(Collectors.toList());
    }

    private void validateInfoPerson(Person person) {
            ValidationInput.validateName(person.getFirstName());
            ValidationInput.validateName(person.getLastName());
            ValidationInput.validateEmail(person.getEmail());
            ValidationInput.validateUserName(person.getPassword());
    }
}
