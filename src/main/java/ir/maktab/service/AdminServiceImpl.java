package ir.maktab.service;

import ir.maktab.data.model.entity.Admin;
import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.RepeatException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.AdminRepository;
import ir.maktab.service.interfaces.AdminService;
import ir.maktab.service.interfaces.PersonService;

import java.util.Objects;

public class AdminServiceImpl implements AdminService {
    private final PersonService personServiceImPl = new PersonServiceImPl();
    private final AdminRepository adminRepository = new AdminRepository();

    @Override
    public void addExpertToSubJob(Expert expert, SubJob subJob) {
        Person expertDb = personServiceImPl.findByUserName(expert.getEmail());
        if (((Expert) expertDb).getSpecialtyStatus().equals(SpecialtyStatus.NewState))
            throw new ValidationException("this Expert isnot confirm ");
        if (((Expert) expertDb).getServicesList().stream().anyMatch(subJob1 -> subJob1.getSubJobName().equals(subJob.getSubJobName())))
            throw new RepeatException("this subject already exist");
        ((Expert) expertDb).getServicesList().add(subJob);
        personServiceImPl.update(expertDb);
    }

    @Override
    public Admin login(String userName, String password) {
        Admin admin = adminRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Admin not found with this userName"));
        if (admin.getPassword().equals(password))
            return admin;
        else
            throw new ValidationException("Your password is incorrect");
    }

    @Override
    public void deleteExpertOfSubJob(Expert expert, SubJob subJob) {
        Person expertDb = personServiceImPl.findByUserName(expert.getEmail());
        if (((Expert) expertDb).getServicesList().isEmpty())
            throw new NotFoundException("subJob not exist");
        ((Expert) expertDb).getServicesList().remove(subJob);
        personServiceImPl.update(expertDb);
    }

    @Override
    public void changePassword(String userName, String passwordOld, String newPassword) {
        Admin person = login(userName, passwordOld);
        person.setPassword(newPassword);
        update(person);
    }

    private void update(Admin admin) {
        if (Objects.isNull(admin))
            throw new NotFoundException("admin not exist");
        adminRepository.update(admin);
    }

    @Override
    public void isConfirmExpertByAdmin(String userName) {
        Person expertDb = personServiceImPl.findByUserName(userName);
        if (((Expert) expertDb).getSpecialtyStatus().equals(SpecialtyStatus.NewState)) {
            ((Expert) expertDb).setSpecialtyStatus(SpecialtyStatus.Confirmed);
        }
        personServiceImPl.update(expertDb);
    }

}

