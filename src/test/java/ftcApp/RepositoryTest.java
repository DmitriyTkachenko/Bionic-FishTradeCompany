package ftcApp;

import ftcApp.model.*;
import ftcApp.model.enums.EmployeeRole;
import ftcApp.model.enums.OrderStatus;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.repository.*;
import ftcApp.service.UserService;
import ftcApp.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RepositoryTest {
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;
    ItemRepository itemRepository;
    ParcelRepository parcelRepository;
    UserService userService;
    OrderRepository orderRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository = new CustomerRepositoryImpl();
        employeeRepository = new EmployeeRepositoryImpl();
        itemRepository = new ItemRepositoryImpl();
        parcelRepository = new ParcelRepositoryImpl();
        userService = new UserServiceImpl();
        orderRepository = new OrderRepositoryImpl();
    }

    @Test
    // User Story 8
    public void testParcelRegistration() throws Exception {
        Parcel parcel = new Parcel(parcelRepository.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, null);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, null);
        Item item3 = new Item("Trout", 200, "", parcel, 6000.0, null);
        parcelRepository.save(parcel);
        Parcel fetched = parcelRepository.findOne(parcel.getId());
        assertEquals(parcel, fetched);
        assertEquals(parcel.getItems(), fetched.getItems());
        parcelRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    // User Story 10
    public void testParcelStatusChange() throws Exception {
        Parcel parcel = new Parcel(parcelRepository.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), null, 2000.0);
        parcelRepository.save(parcel);
        parcelRepository.updateParcelStatus(parcel.getId(), ParcelStatus.PUT_UP_FOR_SALE);
        Parcel fetched = parcelRepository.findOne(parcel.getId());
        assertEquals(ParcelStatus.PUT_UP_FOR_SALE, fetched.getStatus());
        parcelRepository.deleteAll();
    }

    @Test
    // User Story 9, 11
    public void testItemEdit() throws Exception {
        Parcel parcel = new Parcel(parcelRepository.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 2000.0);
        Item item = new Item("Whale", 1000, "", parcel, 10000.0, null);
        itemRepository.save(item);
        Item fetched = itemRepository.findOne(item.getId());
        fetched.setWeight(950);
        itemRepository.save(fetched);
        Item fetched2 = itemRepository.findOne(item.getId());
        assertEquals(950.0, fetched2.getWeight(), 0.001);
        parcelRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    // User Story 5, 16, 21, 24, 28
    public void testRegisterAndLogin() throws Exception {
        Customer customer = new Customer();
        customer.setLogin("johnsmith80");
        customer.setPassword("password80");
        customer.setName("John Smith");
        Employee employee = new Employee();
        employee.setLogin("jenssecurity");
        employee.setPassword("strongPassword418");
        employee.setName("Jens Holder");
        employee.setRole(EmployeeRole.SECURITY_OFFICER);
        userService.save(customer);
        userService.save(employee);

        assertEquals(customer, userService.findByLogin("johnsmith80"));
        assertEquals(employee, userService.findByLogin("jenssecurity"));

        customerRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    // User Story 1
    public void testFindAllItems() throws Exception {
        Parcel parcel = new Parcel(parcelRepository.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, null);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, null);
        Item item3 = new Item("Trout", 200, "", parcel, 6000.0, null);
        parcelRepository.save(parcel);
        List<Item> items = (List<Item>) itemRepository.findAll();
        assertEquals(3, items.size());
        parcelRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    // User Story 2
    public void testSubmitOrder() throws Exception {
        Parcel parcel = new Parcel(parcelRepository.generatePurchaseNumber(), ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 500.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, null);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, null);
        parcelRepository.save(parcel);
        Order order = new Order(OrderStatus.PENDING_PREPAYMENT, null, new ArrayList<>(), new ArrayList<>());
        OrderedItem orderedItem1 = new OrderedItem(item1, 100, order);
        OrderedItem orderedItem2 = new OrderedItem(item2, 50, order);
        orderRepository.save(order);
    }
}