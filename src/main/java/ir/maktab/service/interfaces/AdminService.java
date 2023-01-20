package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Admin;
import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.SubJob;

import java.util.List;

public interface AdminService {
    void addExpertToSubJob(Expert expert, SubJob subJob);

    Admin login(String userName, String password);

    void deleteExpertOfSubJob(Expert expert, SubJob subJob);

    void isConfirmExpertByAdmin(String userName);

    void changePassword(String userName, String passwordOld, String newPassword);

    void saveBasicJob(BasicJob basicJob);

    void saveSubJob(SubJob subJob);

    List<Expert> viewExpertsUnapproved();

    List<Expert> viewAllExpertsApproved();

    List<BasicJob> viewAllBasicJobs();

    List<SubJob> viewAllSubJobs();

    void updateSubJob(SubJob subJob);
}
