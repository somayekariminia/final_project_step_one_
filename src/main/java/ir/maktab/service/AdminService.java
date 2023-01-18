package ir.maktab.service;

import ir.maktab.data.model.entity.Admin;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.SubJob;

public interface AdminService {
    void addExpertToSubJob(Expert expert, SubJob subJob);

    Admin login(String userName, String password);

    void deleteExpertOfSubJob(Expert expert, SubJob subJob);

    void isConfirmExpertByAdmin(Expert expert);
}
