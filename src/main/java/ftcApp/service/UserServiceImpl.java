package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.model.Employee;
import ftcApp.model.User;
import ftcApp.repository.CustomerRepository;
import ftcApp.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    CustomerRepository customerRepository;

    public UserServiceImpl() { }

    @Override
    public User save(User user) {
        if (employeeRepository.existsWithLogin(user.getLogin()) || customerRepository.existsWithLogin(user.getLogin())) {
            throw new RuntimeException("User with this login already exists.");
        } else {
            if (user instanceof Customer) {
                user.setPassword(this.getHashedPassword(user.getPassword()));
                customerRepository.save((Customer)user);
            }
            if (user instanceof Employee) {
                user.setPassword(this.getHashedPassword(user.getPassword()));
                employeeRepository.save((Employee)user);
            }
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {
        Customer customer = customerRepository.findByLogin(login);
        if (customer != null) {
            return customer;
        }
        Employee employee = employeeRepository.findByLogin(login);
        if (employee != null) {
            return employee;
        }
        return null;
    }

    private String getHashedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
