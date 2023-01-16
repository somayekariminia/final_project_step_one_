package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.PersonInRepository;

public class UsersServiceImpl implements PersonService<Expert> {
    private PersonInRepository personInRepository=PersonInRepository.getInstance();
    @Override
    public void save(Expert person) {
        checkImage(person.getExpertImage());
        personInRepository.save(person);
    }

    private void checkImage(byte[] expertImage){
         if(expertImage.length/1024<=300)
             throw  new ValidationException("size image is bigger of 300 kb");
    }
}
