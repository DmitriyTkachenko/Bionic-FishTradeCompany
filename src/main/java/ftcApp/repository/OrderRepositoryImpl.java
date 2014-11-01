package ftcApp.repository;

import ftcApp.model.Order;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class OrderRepositoryImpl extends RepositoryImpl<Order, Integer> implements OrderRepository {
    public OrderRepositoryImpl() { super(Order.class); }
}
