package ftcApp.service;

import ftcApp.model.Order;
import ftcApp.model.enums.OrderStatus;

public interface OrderService extends GenericService<Order, Integer> {
    void saveOrderForCustomerWithLogin(Order order, String login);
    Iterable<Order> findOrdersPendingShipment();
    Iterable<Order> findOrdersNotPaidInFull();
    void updateOrderStatus(Order order, OrderStatus status);
    void markOrderAsShipped(Order order);
    void addPaymentToOrder(Order order, double sum);
}
