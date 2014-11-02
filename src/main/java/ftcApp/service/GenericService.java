package ftcApp.service;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    void delete (ID id);
    void deleteAll();
    Iterable<T> findAll();
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
