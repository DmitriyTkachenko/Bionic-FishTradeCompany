package ftcApp.service;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    Iterable<T> updateAll(Iterable<T> entities);
    void delete(ID id);
    void delete(T entity);
    void deleteAll();
    Iterable<T> findAll();
    Iterable<T> findAllAndRefresh();
    T findOne(ID id);
    T findOneAndRefresh(ID id);
    boolean exists(ID id);
    long count();
}
