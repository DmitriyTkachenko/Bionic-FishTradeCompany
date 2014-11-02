package ftcApp.service;

import ftcApp.model.Order;
import ftcApp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {
    @Inject
    OrderServiceImpl(OrderRepository repository) {
        super(repository, Order.class);
    }
}
