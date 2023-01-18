package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;

import java.io.File;
import java.util.List;

public interface PersonService {
    void save(Person t, File file);

    Person findByUserName(String userName);

    public void update(Person person);
    List<Person> findAllExpertsIsNotConfirm();
}
