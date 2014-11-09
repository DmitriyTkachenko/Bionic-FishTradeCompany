package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.model.Order;
import ftcApp.model.Payment;
import ftcApp.model.enums.OrderStatus;
import ftcApp.repository.CustomerRepository;
import ftcApp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {
    @Inject
    private CustomerRepository customerRepository;

    @Inject
    OrderServiceImpl(OrderRepository repository) {
        super(repository, Order.class);
    }

    @Override
    public void saveOrderForCustomerWithLogin(Order order, String login) {
        Customer customer = customerRepository.findByLogin(login);
        order.setCustomer(customer);
        if (Double.compare(customer.getPrepaymentShareRequired(), 0.0) > 0) {
            order.setStatus(OrderStatus.PENDING_PREPAYMENT);
        } else {
            order.setStatus(OrderStatus.PENDING_SHIPMENT);
        }
        order.setCreated(new Date());
        repository.save(order);
    }

    @Override
    public Iterable<Order> findOrdersPendingShipment() {
        return ((OrderRepository)repository).findOrdersPendingShipment();
    }

    @Override
    public Iterable<Order> findOrdersNotPaidInFull() {
        return ((OrderRepository)repository).findOrdersNotPaidInFull();
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
    public void addPaymentToOrder(Order order, double sum) {
        order.getPayments().add(new Payment(order, sum));
        repository.update(order);
    }
}
