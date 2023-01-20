package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.data.repository.ExpertRepository;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.util.UtilImage;
import ir.maktab.util.ValidationInput;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ExpertService implements ir.maktab.service.interfaces.ExpertService {
    private final ExpertRepository expertRepository = ExpertRepository.getInstance();

    @Override
    public void save(Expert expert, File file) {
        validateInfoPerson(expert);
        expert.setExpertImage(UtilImage.validateImage(file));
        expert.setSpecialtyStatus(SpecialtyStatus.NewState);
        expert.setPerformance(0);
        expertRepository.save(expert);

    }

    @Override
    public Expert login(String userName, String password) {
        Expert expert = expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Expert not found with this userName"));
        if (expert.getPassword().equals(password))
            return expert;
        else
            throw new ValidationException("Your password is incorrect");
    }

    @Override
    public void changePassword(String userName, String passwordOld, String newPassword) {
        Expert expert = login(userName, passwordOld);
        expert.setPassword(newPassword);
        update(expert);
    }

    @Override
    public Expert findByUserName(String userName) {
        return expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
    }

    @Override
    public void update(Expert expert) {
        if (Objects.isNull(expert))
            throw new NotFoundException("person is not exist");
        expertRepository.update(expert);
    }

    @Override
    public List<Expert> findAllPerson() {
        List<Expert> listExpert = expertRepository.getAll();
        if (listExpert.isEmpty())
            throw new NotFoundException("there arent experts");
        return listExpert;
    }

    @Override
    public List<Expert> findAllExpertsApproved() {
        return findAllPerson().stream().filter(expert -> expert.getSpecialtyStatus().equals(SpecialtyStatus.Confirmed)).
                collect(Collectors.toList());
    }

    @Override
    public List<Expert> findAllExpertsIsNotConfirm() {
        return findAllPerson().stream().filter(expert -> expert.
                        getSpecialtyStatus().equals(SpecialtyStatus.NewState)).
                collect(Collectors.toList());
    }

    private void validateInfoPerson(Expert person) {
        ValidationInput.validateName(person.getFirstName());
        ValidationInput.validateName(person.getLastName());
        ValidationInput.validateEmail(person.getEmail());
        ValidationInput.validateUserName(person.getPassword());
    }
}
