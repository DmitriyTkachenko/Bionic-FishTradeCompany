package ftcApp;

import ftcApp.model.*;
import ftcApp.model.enums.OrderStatus;
import ftcApp.model.enums.ParcelStatus;
import ftcApp.model.enums.UserRole;
import ftcApp.service.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Named
public class RepositoryTest {
    CustomerService customerService;
    EmployeeService employeeService;
    ItemService itemService;
    ParcelService parcelService;
    UserService userService;
    OrderService orderService;
    OrderedItemService orderedItemService;
    PaymentService paymentService;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
        customerService = context.getBean(CustomerService.class);
        employeeService = context.getBean(EmployeeService.class);
        itemService = context.getBean(ItemService.class);
        parcelService = context.getBean(ParcelService.class);
        userService = context.getBean(UserService.class);
        orderService = context.getBean(OrderService.class);
        orderedItemService = context.getBean(OrderedItemService.class);
        paymentService = context.getBean(PaymentService.class);
    }

    @Test
    // User Story 8
    public void testParcelRegistration() throws Exception {
        Parcel parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, null);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, null);
        Item item3 = new Item("Trout", 200, "", parcel, 6000.0, null);
        parcelService.save(parcel);
        Parcel fetched = parcelService.findOne(parcel.getId());

        assertEquals(parcel, fetched);
        assertEquals(parcel.getItems(), fetched.getItems());

        itemService.deleteAll();
        parcelService.deleteAll();
    }

    @Test
    // User Story 10
    public void testParcelStatusChange() throws Exception {
        Parcel parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), null, 2000.0);
        parcelService.save(parcel);
        parcelService.updateParcelStatus(parcel.getId(), ParcelStatus.PUT_UP_FOR_SALE);
        Parcel fetched = parcelService.findOne(parcel.getId());

        assertEquals(ParcelStatus.PUT_UP_FOR_SALE, fetched.getStatus());

        parcelService.deleteAll();
    }

    @Test
    // User Story 9, 11
    public void testItemEdit() throws Exception {
        Parcel parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 2000.0);
        Item item = new Item("Whale", 1000, "", parcel, 10000.0, null);
        itemService.save(item);
        Item fetched = itemService.findOne(item.getId());
        fetched.setWeightBought(950);
        itemService.update(fetched);
        Item fetched2 = itemService.findOne(item.getId());

        assertEquals(950.0, fetched2.getWeightBought(), 0.001);

        itemService.deleteAll();
        parcelService.deleteAll();
    }

    @Test
    // User Story 5, 15, 20, 23, 27
    public void testRegisterAndLogin() throws Exception {
        Customer customer = new Customer();
        customer.setLogin("johnsmith80");
        customer.setPassword("password80");
        customer.setName("John Smith");
        Employee employee = new Employee();
        employee.setLogin("jenssecurity");
        employee.setPassword("strongPassword418");
        employee.setName("Jens Holder");
        employee.setUserRole(UserRole.SECURITY_OFFICER);
        userService.save(customer);
        userService.save(employee);

        assertEquals(customer, userService.findByLogin("johnsmith80"));
        assertEquals(employee, userService.findByLogin("jenssecurity"));

        customerService.deleteAll();
        employeeService.deleteAll();
    }

    @Test
    // User Story 1
    public void testFindAllItems() throws Exception {
        Parcel parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 1000.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, null);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, null);
        Item item3 = new Item("Trout", 200, "", parcel, 6000.0, null);
        parcelService.save(parcel);
        List<Item> items = (List<Item>) itemService.findAll();

        assertEquals(3, items.size());

        itemService.deleteAll();
        parcelService.deleteAll();
    }

    @Test
    // User Story 2, 4, 22
    public void testSubmitOrder() throws Exception {
        Parcel parcel = new Parcel(ParcelStatus.REGISTERED_BY_GM, new Date(), new ArrayList<>(), 500.0);
        Item item1 = new Item("Cod", 300, "", parcel, 5000.0, 5200.0);
        Item item2 = new Item("Tuna", 400, "", parcel, 3500.0, 3600.0);
        parcelService.save(parcel);
        Order order = new Order(OrderStatus.PENDING_PREPAYMENT, null, null, new ArrayList<>(), new ArrayList<>(), null);
        OrderedItem orderedItem1 = new OrderedItem(item1, 100, item1.getSellingPrice(), order);
        OrderedItem orderedItem2 = new OrderedItem(item2, 50, item1.getSellingPrice(), order);
        Payment p1 = new Payment(order, 100000.0);
        Payment p2 = new Payment(order, 50000.0);
        Payment p3 = new Payment(order, 25000.0);
        orderService.save(order);
        Order fetched = orderService.findOne(order.getId());

        assertEquals(order, fetched);
        assertEquals(order.getOrderedItems(), fetched.getOrderedItems());
        assertEquals(700000.0, order.getTotalPrice(), 0.001);
        assertEquals(150, order.getTotalWeight(), 0.001);
        assertEquals(25.0, order.getPercentagePaid(), 0.001);

        orderedItemService.deleteAll();
        paymentService.deleteAll();
        itemService.deleteAll();
        parcelService.deleteAll();
        orderService.deleteAll();
    }
}