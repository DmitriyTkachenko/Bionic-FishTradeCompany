package ftcApp;

import ftcApp.model.Customer;
import ftcApp.model.Employee;
import ftcApp.model.User;
import ftcApp.model.enums.EmployeeRole;
import ftcApp.repository.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RepositoryTest {
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;
    ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository = new CustomerRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
        itemRepository = new ItemRepositoryImpl();
    }

    @Test
    public void test1() throws Exception {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setLogin("WildSpirit");
        customer.setPassword("kittenricky");
        customer.setPrepaymentShareRequired(0);
        customerRepository.save(customer);
        Customer a = customerRepository.findOne(1);
        assertEquals(customer, a);
        User employee = new Employee();
        employee.setLogin("Spirit");
        employee.setPassword("kittenricky");
        ((Employee)employee).setRole(EmployeeRole.GENERAL_MANAGER);
        employeeRepository.save((Employee)employee);
        Employee b = employeeRepository.findOne(2);
        if (employee instanceof Customer) {
            System.out.println("FUCK");
        }
        System.out.println("Done");
    }
}