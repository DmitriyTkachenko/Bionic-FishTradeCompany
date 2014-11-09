package ftcApp.service;

import ftcApp.model.User;

public interface UserService {
    User save(User user);
    User findByLogin(String login);
    boolean existsWithLogin(String login);
}
