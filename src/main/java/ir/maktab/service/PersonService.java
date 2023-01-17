package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.Person;

public interface PersonService<T extends Person> {
    void save(T t );
    Expert findByUserName(String userName);
    public void update(Expert expert);
}
