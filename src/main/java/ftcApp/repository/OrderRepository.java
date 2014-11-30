package ftcApp.repository;

import ftcApp.model.Order;

import java.util.Date;

public interface OrderRepository extends GenericRepository<Order, Integer> {
    Iterable<Order> findOrdersPendingShipment();
    Iterable<Order> findOrdersNotPaidInFull();
    Iterable<Order> findCompletedOrdersBetweenDates(Date start, Date end);
}
