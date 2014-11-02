package ftcApp.repository;

import ftcApp.model.User;

import java.io.Serializable;

public interface UserRepository<T extends User, ID extends Serializable> extends GenericRepository<T, ID> {
    T findByLogin(String login);
    boolean existsWithLogin(String login);
}
