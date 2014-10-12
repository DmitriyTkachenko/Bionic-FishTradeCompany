package ftcApp;

import java.io.Serializable;

public interface Repository<T, ID extends Serializable> {
    T save(T entity);
    void delete (ID id);
    Iterable<T> findAll();
    T findOne(ID id);
    boolean exists(ID id);
    long count();
}
