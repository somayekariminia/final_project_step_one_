package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Expert;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExpertService {
    void save(Expert t, File file) throws IOException;

    Expert findByUserName(String userName);

    Expert login(String userName, String password);

    void changePassword(String userName, String passwordOld, String newPassword);

    List<Expert> findAllPerson();

    void update(Expert person);

    List<Expert> findAllExpertsIsNotConfirm();

    List<Expert> findAllExpertsApproved();

}
