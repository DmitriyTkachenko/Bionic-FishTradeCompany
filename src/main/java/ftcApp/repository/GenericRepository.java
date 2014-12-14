package ftcApp.repository;

import java.io.Serializable;

public interface GenericRepository<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    T refresh(T entity);
    Iterable<T> refreshAll(Iterable<T> entities);
    void delete(T entity);
    void delete(ID id);
    void deleteAll();
    Iterable<T> findAll();
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
