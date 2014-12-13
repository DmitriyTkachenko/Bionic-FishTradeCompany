package ftcApp.service;

import ftcApp.exception.OrderSaveFailedException;
import ftcApp.model.*;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.model.enums.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Inject
    private ItemService itemService;

    @Inject
    private OrderService orderService;

    @Inject
    private FishNameService fishNameService;

    private Parcel parcel;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Customer customer;
    private Order order1;
    private Order order2;
    private OrderedItem orderedItem11;
    private OrderedItem orderedItem12;
    private OrderedItem orderedItem21;
    private OrderedItem orderedItem22;
    private List<String> fishNames;

    private boolean previousItemsDeleted = true;
    private boolean previousEmployeesDeleted = true;
    private boolean previousCustomerDeleted = true;
    private boolean previousOrdersDeleted = true;
    private boolean previousFishNamesDeleted = true;

    public TestServiceImpl() {
        fishNames = Arrays.asList("Hake", "Plaice", "Mullet", "Pollock", "Haddock", "Wolffish", "Ling", "Saury",
                "Coalfish", "Clupea", "Sardine", "Perch", "Scomber", "Blue marlin", "Tarpon", "Spiny dogfish", "Tuna",
                "Halibut", "Atka mackerel", "Conger");
    }

    @Override
    public void addItemsData() {
        if (!previousItemsDeleted) {
            return;
        }
        parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "Country of origin: Norway", parcel, 5000.0, 5100.0);
        item1.duplicateBoughtAndColdStoreProperties();
        Item item2 = new Item("Tuna", 400, "Country of origin: Thailand", parcel, 3500.0, 3580.0);
        item2.duplicateBoughtAndColdStoreProperties();
        Item item3 = new Item("Trout", 200, "Country of origin: Turkey", parcel, 6000.0, 6120.0);
        item3.duplicateBoughtAndColdStoreProperties();
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
        employee1 = new Employee("GM", "pass", "John Smith", UserRole.GENERAL_MANAGER);
        employee2 = new Employee("CSM", "pass", "Jens Eriksson", UserRole.COLD_STORE_MANAGER);
        employee3 = new Employee("ACC", "pass", "Jessica Logan", UserRole.ACCOUNTANT);
        employee4 = new Employee("SO", "pass", "Markus Rosenberg", UserRole.SECURITY_OFFICER);
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
        customer = new Customer("WildSpirit", "pass", "Dmitriy", UserRole.CUSTOMER);
        customer.setPrepaymentShareRequired(0.2);
        customer.setShippingAddress("51 Predslavinskaya str.");
        customer.setEmail("wildspirit2009@gmail.com");
        userService.save(customer);
        previousCustomerDeleted = false;
    }

    @Override
    public void removeCustomer() {
        customerService.delete(customer);
        previousCustomerDeleted = true;
    }

    @Override
    public void addOrders() {
        if (!previousOrdersDeleted) {
            return;
        }
        Item item1 = itemService.findOne(1);
        Item item2 = itemService.findOne(2);
        Item item3 = itemService.findOne(3);
        order1 = new Order();
        orderedItem11 = new OrderedItem(item1, 10, item1.getSellingPrice(), order1);
        orderedItem12 = new OrderedItem(item2, 5, item2.getSellingPrice(), order1);
        order2 = new Order();
        orderedItem21 = new OrderedItem(item2, 15, item2.getSellingPrice(), order2);
        orderedItem22 = new OrderedItem(item3, 17.5, item3.getSellingPrice(), order2);
        try {
            orderService.saveOrderForCustomerWithLogin(order1, "WildSpirit");
            orderService.saveOrderForCustomerWithLogin(order2, "WildSpirit");
        } catch (OrderSaveFailedException e) {
            e.printStackTrace();
        }
        orderService.markOrderAsCompleted(order1);
        orderService.markOrderAsCompleted(order2);
        previousOrdersDeleted = false;
    }

    @Override
    public void removeOrders() {
        orderService.delete(order1);
        orderService.delete(order2);
    }

    @Override
    public void addFishNames() {
        if (!previousFishNamesDeleted) {
            return;
        }
        for (String name : fishNames) {
            FishName fishName = new FishName();
            fishName.setName(name);
            fishNameService.save(fishName);
        }
    }

    @Override
    public void removeFishNames() {
        fishNameService.deleteAll();
    }

}
