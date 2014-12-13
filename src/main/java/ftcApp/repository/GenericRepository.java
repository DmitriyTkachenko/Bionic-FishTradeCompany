package ftcApp.repository;

import java.io.Serializable;

public interface GenericRepository<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    void delete(ID id);
    void deleteAll();
    Iterable<T> findAll();
    Iterable<T> findAllAndRefresh();
    T findOne(ID id);
    T findOneAndRefresh(ID id);
    boolean exists(ID id);
    long count();
}
