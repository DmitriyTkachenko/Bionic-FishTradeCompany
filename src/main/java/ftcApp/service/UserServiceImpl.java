package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.model.Employee;
import ftcApp.model.User;
import ftcApp.repository.CustomerRepository;
import ftcApp.repository.CustomerRepositoryImpl;
import ftcApp.repository.EmployeeRepository;
import ftcApp.repository.EmployeeRepositoryImpl;

public class UserServiceImpl implements UserService {
    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
    CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Override
    public User save(User user) {
        if (employeeRepository.existsWithLogin(user.getLogin()) || customerRepository.existsWithLogin(user.getLogin())) {
            throw new RuntimeException("User with this login already exists.");
        } else {
            if (user instanceof Customer) {
                customerRepository.save((Customer)user);
            }
            if (user instanceof Employee) {
                employeeRepository.save((Employee)user);
            }
        }
        return user;
    }

    @Override
    public User findByLogin(String login) {
        Employee employee = employeeRepository.findByLogin(login);
        if (employee != null) {
            return employee;
        }
        Customer customer = customerRepository.findByLogin(login);
        if (customer != null) {
            return customer;
        }
        return null;
    }
}
