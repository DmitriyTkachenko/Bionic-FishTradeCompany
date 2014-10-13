package ftcApp.repository;

import java.io.Serializable;

public interface Repository<T, ID extends Serializable> {
    T save(T entity);
    void delete (ID id);
    void deleteAll();
    Iterable<T> findAll();
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
