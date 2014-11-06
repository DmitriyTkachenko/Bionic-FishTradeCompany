package ftcApp.service;

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

    private Parcel parcel;
    private Employee employee;

    private boolean previousItemsDeleted = true;
    private boolean previousUserDeleted = true;

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
    public void addUser() {
        if (!previousUserDeleted) {
            return;
        }
        employee = new Employee("Admin", "test", UserRole.SECURITY_OFFICER);
        userService.save(employee);
        previousUserDeleted = false;
    }

    @Override
    public void removeUser() {
        employeeService.delete(employee);
        previousUserDeleted = true;
    }

}
