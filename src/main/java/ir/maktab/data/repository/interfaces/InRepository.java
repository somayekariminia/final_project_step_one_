package ir.maktab.data.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface InRepository<T, L> {
    void save(T t);

    Optional<T> getById(L l);

    List<T> getAll();

    void update(T t);

    void delete(T t);

}
