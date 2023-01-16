package ir.maktab.service;

import ir.maktab.data.model.entity.Person;

public interface PersonService<T extends Person> {
    void save(T t );
}
