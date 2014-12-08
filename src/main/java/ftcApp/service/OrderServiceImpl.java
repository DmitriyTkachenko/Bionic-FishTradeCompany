package ftcApp.service;

import ftcApp.exception.OrderSaveFailedException;
import ftcApp.model.*;
import ftcApp.model.enums.OrderStatus;
import ftcApp.repository.CustomerRepository;
import ftcApp.repository.ItemRepository;
import ftcApp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {
    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private UserService userService;

    @Inject
    OrderServiceImpl(OrderRepository repository) {
        super(repository, Order.class);
    }

    @Override
    public void saveOrderForCustomerWithLogin(Order order, String login) throws OrderSaveFailedException {
        Customer customer = customerRepository.findByLogin(login);
        order.setCustomer(customer);
        if (Double.compare(customer.getPrepaymentShareRequired(), 0.0) > 0) {
            order.setStatus(OrderStatus.PENDING_PREPAYMENT);
        } else {
            order.setStatus(OrderStatus.PENDING_SHIPMENT);
        }
        order.setCreated(new Date());
        this.updateItemsAccordingToOrderedQuantities(order);
        repository.save(order);
    }

    private void updateItemsAccordingToOrderedQuantities(Order order) throws OrderSaveFailedException {
        List<OrderedItem> orderedItems = order.getOrderedItems();
        for (OrderedItem orderedItem : orderedItems) {
            Item item = orderedItem.getItem();
            item = itemRepository.findOne(item.getId());
            orderedItem.setItem(item);
            item.reduceWeightInColdStoreBy(orderedItem.getWeight());
            itemRepository.update(item);
        }
    }

    @Override
    public Iterable<Order> findOrdersPendingShipment() {
        return ((OrderRepository) repository).findOrdersPendingShipment();
    }

    @Override
    public Iterable<Order> findOrdersNotPaidInFull() {
        return ((OrderRepository) repository).findOrdersNotPaidInFull();
    }

    @Override
    public void updateOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        repository.update(order);
    }

    @Override
    public void markOrderAsShipped(Order order) {
        order.setStatus(OrderStatus.SHIPPED);
        order.setShipped(new Date());
        repository.update(order);
    }

    @Override
    public void markOrderAsCompleted(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        order.setCompleted(new Date());
        repository.update(order);
    }

    @Override
    public void addPaymentToOrder(Order order, double sum) {
        order.getPayments().add(new Payment(order, sum));
        repository.update(order);
    }

    @Override
    public Iterable<Order> findCompletedOrdersBetweenDates(Date start, Date end) {
        LocalDateTime endldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(end.getTime()), ZoneId.systemDefault());
        endldt = endldt.plusDays(1);
        end = Date.from(endldt.atZone(ZoneId.systemDefault()).toInstant());
        return ((OrderRepository) repository).findCompletedOrdersBetweenDates(start, end);
    }

    @Override
    public Iterable<Order> findOrdersByLoggedCustomer() {
        String login = userService.getLoginOfCurrentlyLoggedUser();
        Customer customer = customerRepository.findByLogin(login);
        return ((OrderRepository) repository).findOrdersByCustomer(customer);
    }
}
