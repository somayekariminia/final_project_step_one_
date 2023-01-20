package ir.maktab.service;

import ir.maktab.data.model.entity.Admin;
import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.data.repository.AdminRepository;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.RepeatException;
import ir.maktab.exception.ValidationException;
import ir.maktab.service.interfaces.AdminService;
import ir.maktab.service.interfaces.ExpertService;
import ir.maktab.service.interfaces.SubJobService;

import java.util.List;
import java.util.Objects;

public class AdminServiceImpl implements AdminService {
    private final ExpertService expertServiceImPl =ExpertServiceImpl.getInstance();
    private final AdminRepository adminRepository = new AdminRepository();
    private final BasicJobsService basicJobsService = BasicJobsService.getInstance();
    private final SubJobService subJobService = SubJobServiceImpl.getInstance();

    @Override
    public void saveBasicJob(BasicJob basicJob) {
        basicJobsService.save(basicJob);
    }

    @Override
    public void saveSubJob(SubJob subJob) {
        subJobService.save(subJob);
    }

    @Override
    public void addExpertToSubJob(Expert expert, SubJob subJob) {
        Expert expertDb = expertServiceImPl.findByUserName(expert.getEmail());
        SubJob subJobDb = subJobService.finByName(subJob.getSubJobName());
        if (expertDb.getSpecialtyStatus().equals(SpecialtyStatus.NewState))
            throw new ValidationException("this Expert isnot confirm ");
        if (expertDb.getServicesList().stream().anyMatch(subJob1 -> subJob1.getSubJobName().equals(subJobDb.getSubJobName())))
            throw new RepeatException("this subject already exist");
        expertDb.getServicesList().add(subJobDb);
        expertServiceImPl.update(expertDb);
    }

    @Override
    public Admin login(String userName, String password) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Admin not found with this userName"));
        if (admin.getPassword().equals(password)) return admin;
        else throw new ValidationException("Your password is incorrect");
    }

    @Override
    public void deleteExpertOfSubJob(Expert expert, SubJob subJob) {
        Expert expertDb = expertServiceImPl.findByUserName(expert.getEmail());
        SubJob subJobDb = subJobService.finByName(subJob.getSubJobName());
        if (expertDb.getServicesList().isEmpty()) throw new NotFoundException("subJob not exist");
        expertDb.getServicesList().remove(subJobDb);
        expertServiceImPl.update(expertDb);
    }

    @Override
    public void changePassword(String userName, String passwordOld, String newPassword) {
        Admin person = login(userName, passwordOld);
        person.setPassword(newPassword);
        update(person);
    }

    @Override
    public void updateSubJob(SubJob subJob) {
        subJobService.updateSubJob(subJob);
    }

    @Override
    public List<Expert> viewExpertsUnapproved() {
        List<Expert> allExpertsIsNotConfirm = expertServiceImPl.findAllExpertsIsNotConfirm();
        if (allExpertsIsNotConfirm.isEmpty()) throw new NotFoundException("There are no unapproved experts ");
        return allExpertsIsNotConfirm;
    }

    private void update(Admin admin) {
        if (Objects.isNull(admin)) throw new NotFoundException("admin not exist");
        adminRepository.update(admin);
    }

    @Override
    public List<Expert> viewAllExpertsApproved() {
        return expertServiceImPl.findAllExpertsApproved();
    }

    @Override
    public List<BasicJob> viewAllBasicJobs() {
        List<BasicJob> listBasicJobs = basicJobsService.findAllBasicJobs();
        if (listBasicJobs.isEmpty()) throw new NotFoundException("there arent basicJobs");
        return basicJobsService.findAllBasicJobs();
    }

    @Override
    public List<SubJob> viewAllSubJobs() {
        List<SubJob> subJobList = subJobService.findAll();
        if (subJobList.isEmpty()) throw new NotFoundException("");
        return subJobList;
    }

    @Override
    public void isConfirmExpertByAdmin(String userName) {
        Expert expertDb = expertServiceImPl.findByUserName(userName);
        if (expertDb.getSpecialtyStatus().equals(SpecialtyStatus.NewState)) {
            expertDb.setSpecialtyStatus(SpecialtyStatus.Confirmed);
        }
        expertServiceImPl.update(expertDb);
    }
}

