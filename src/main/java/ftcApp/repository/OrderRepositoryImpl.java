package ftcApp.repository;

import ftcApp.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Order, Integer> implements OrderRepository {
    public OrderRepositoryImpl() { super(Order.class); }
}
