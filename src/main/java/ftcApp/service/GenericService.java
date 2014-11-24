package ftcApp.service;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    Iterable<T> updateAll(Iterable<T> entities);
    void delete(ID id);
    void delete(T entity);
    void deleteAll();
    void refresh(T entity);
    Iterable<T> findAll();
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
