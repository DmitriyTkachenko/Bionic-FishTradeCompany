package ftcApp.repository;

import ftcApp.model.Order;

public interface OrderRepository extends GenericRepository<Order, Integer> {
    Iterable<Order> findOrdersPendingShipment();
    Iterable<Order> findOrdersNotPaidInFull();
}
