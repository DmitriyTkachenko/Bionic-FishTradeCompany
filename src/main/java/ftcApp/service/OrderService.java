package ftcApp.service;

import ftcApp.model.Order;

public interface OrderService extends GenericService<Order, Integer> {
    void saveOrderForCustomerWithLogin(Order order, String login);
}
