package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.model.Order;
import ftcApp.model.User;
import ftcApp.repository.CustomerRepository;
import ftcApp.repository.OrderRepository;
import ftcApp.repository.UserRepository;
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
        User user = customerRepository.findByLogin(login);
        order.setCustomer((Customer)user);
        order.setCreated(new Date());
        repository.save(order);
    }
}
