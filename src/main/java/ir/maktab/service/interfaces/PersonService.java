package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.model.entity.SubJob;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PersonService {
    void save(Person t, File file) throws IOException;

    Person findByUserName(String userName);

    Person login(String userName, String password);

    void changePassword(String userName, String passwordOld, String newPassword);

    List<Person> findAllPerson();

    void update(Person person);

    List<Person> findAllExpertsIsNotConfirm();

    List<Person> findAllExpertsApproved();

    List<BasicJob> viewAllBasicJobs();

    List<SubJob> viewSubJobsABasicJob(String nameBasicJob);
}
