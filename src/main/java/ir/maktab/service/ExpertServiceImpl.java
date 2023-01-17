package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.ExpertRepository;
import ir.maktab.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;


public class ExpertServiceImpl implements PersonService {
    private final ExpertRepository expertRepository = ExpertRepository.getInstance();
    private final PersonRepository personRepository = new PersonRepository();

 /*   @Override
    public void save(Person person) {
        checkImage(person.getExpertImage());
        expertRepository.save(person);
    }*/

    public void save(Person person) {
        if (person instanceof Expert)
            checkImage(((Expert) person).getExpertImage());
        personRepository.save(person);
    }

    public Person login(String userName, String password) {
        Person expert = expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
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

    private void checkImage(byte[] expertImage) {
        if ((expertImage.length / 1024) >= 300)
            throw new ValidationException("size image is bigger of 300 kb");
    }

    public Expert findByUserName(String userName) {
        return expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
    }

    @Override
    public void update(Person person) {
        personRepository.update(person);
    }

    public List<Person> findAllExperts() {
        List<Person> listExpert = personRepository.getAll();
        if (listExpert.isEmpty())
            throw new NotFoundException("List Experts Are empty");
        return listExpert;
    }

    public List<Person> findAllExpertsIsNotConfirm() {
        return findAllExperts().stream().
                filter(person -> person instanceof Expert && ((Expert) person).getSpecialtyStatus().equals(SpecialtyStatus.NewState)).collect(Collectors.toList());
    }
}
