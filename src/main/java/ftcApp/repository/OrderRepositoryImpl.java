package ftcApp.repository;

import ftcApp.model.Customer;
import ftcApp.model.Order;
import ftcApp.model.enums.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Order, Integer> implements OrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public Iterable<Order> findOrdersPendingShipment() {
        Iterable<Order> result = null;
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.status = :orderStatus", entityClass)
                    .setParameter("orderStatus", OrderStatus.PENDING_SHIPMENT);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Order> findOrdersNotPaidInFull() {
        Iterable<Order> result = null;
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.status != :orderStatus", entityClass)
                    .setParameter("orderStatus", OrderStatus.COMPLETED);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Order> findCompletedOrdersBetweenDates(Date start, Date end) {
        Iterable<Order> result = null;
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE (o.completed >= :start AND o.completed < :end) AND o.status = :orderStatus", entityClass)
                    .setParameter("start", start).setParameter("end", end).setParameter("orderStatus", OrderStatus.COMPLETED);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Iterable<Order> findOrdersByCustomer(Customer customer) {
        Iterable<Order> result = null;
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.customer = :customer", entityClass)
                    .setParameter("customer", customer);
            result = query.getResultList();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }


}
