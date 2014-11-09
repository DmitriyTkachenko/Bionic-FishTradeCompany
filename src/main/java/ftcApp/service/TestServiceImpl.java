package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.model.Employee;
import ftcApp.model.Item;
import ftcApp.model.Parcel;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.model.enums.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    @Inject
    private ParcelService parcelService;

    @Inject
    private UserService userService;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private CustomerService customerService;

    private Parcel parcel;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Customer customer;

    private boolean previousItemsDeleted = true;
    private boolean previousEmployeesDeleted = true;
    private boolean previousCustomerDeleted = true;

    @Override
    public void addItemsData() {
        if (!previousItemsDeleted) {
            return;
        }
        parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "Country of origin: Norway", parcel, 5000.0, 305.0);
        Item item2 = new Item("Tuna", 400, "Country of origin: Thailand", parcel, 3500.0, 407.0);
        Item item3 = new Item("Trout", 200, "Country of origin: Turkey", parcel, 6000.0, 202.5);
        parcelService.save(parcel);
        previousItemsDeleted = false;
    }

    @Override
    public void removeItemsData() {
        parcelService.delete(parcel);
        previousItemsDeleted = true;
    }

    @Override
    public void addEmployees() {
        if (!previousEmployeesDeleted) {
            return;
        }
        employee1 = new Employee("GM", "pass", UserRole.GENERAL_MANAGER);
        employee2 = new Employee("CSM", "pass", UserRole.COLD_STORE_MANAGER);
        employee3 = new Employee("ACC", "pass", UserRole.ACCOUNTANT);
        employee4 = new Employee("SO", "pass", UserRole.SECURITY_OFFICER);
        userService.save(employee1);
        userService.save(employee2);
        userService.save(employee3);
        userService.save(employee4);
        previousEmployeesDeleted = false;
    }

    @Override
    public void removeEmployees() {
        employeeService.delete(employee1);
        employeeService.delete(employee2);
        employeeService.delete(employee3);
        employeeService.delete(employee4);
        previousEmployeesDeleted = true;
    }

    @Override
    public void addCustomer() {
        if (!previousCustomerDeleted) {
            return;
        }
        customer = new Customer("WildSpirit", "pass", UserRole.CUSTOMER);
        customer.setPrepaymentShareRequired(0.2);
        userService.save(customer);
        previousCustomerDeleted = false;
    }

    @Override
    public void removeCustomer() {
        customerService.delete(customer);
        previousCustomerDeleted = true;
    }

}
