package ftcApp.service;

import ftcApp.exception.OrderSaveFailedException;
import ftcApp.model.Order;
import ftcApp.model.enums.OrderStatus;

import java.util.Date;

public interface OrderService extends GenericService<Order, Integer> {
    void saveOrderForCustomerWithLogin(Order order, String login) throws OrderSaveFailedException;
    Iterable<Order> findOrdersPendingShipment();
    Iterable<Order> findOrdersNotPaidInFull();
    void updateOrderStatus(Order order, OrderStatus status);
    void markOrderAsShipped(Order order);
    void markOrderAsCompleted(Order order);
    void addPaymentToOrder(Order order, double sum);
    Iterable<Order> findCompletedOrdersBetweenDates(Date start, Date end);
    Iterable<Order> findOrdersByLoggedCustomer();
}
