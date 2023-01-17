package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.ExpertRepository;


public class ExpertServiceImpl implements PersonService<Expert> {
    private final ExpertRepository expertRepository = ExpertRepository.getInstance();

    @Override
    public void save(Expert person) {
        checkImage(person.getExpertImage());
        expertRepository.save(person);
    }

    public Expert login(String userName, String password) {
        Expert expert = expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
        if (expert.getPassword().equals(password))
            return expert;
        else
            throw new ValidationException("Your password is incorrect");
    }

    public void changePassword(String userName, String passwordOld, String newPassword) {
        Expert expert = login(userName, passwordOld);
        expert.setPassword(newPassword);
        expertRepository.update(expert);
    }

    private void checkImage(byte[] expertImage) {
        if (expertImage.length / 1024 <= 300)
            throw new ValidationException("size image is bigger of 300 kb");
    }
    public Expert findByUserName(String userName){
        return expertRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Person not found with this userName"));
    }

    public void update(Expert expert){
        expertRepository.update(expert);
    }

}