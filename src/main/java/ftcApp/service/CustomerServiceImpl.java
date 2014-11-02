package ftcApp.service;

import ftcApp.model.Customer;
import ftcApp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class CustomerServiceImpl extends GenericServiceImpl<Customer, Integer> implements CustomerService {
    @Inject
    CustomerServiceImpl(CustomerRepository repository) {
        super(repository, Customer.class);
    }

    @Override
    public void updateRequiredPrepaymentShare(Integer id, double prepaymentShare) {
        ((CustomerRepository)repository).updateRequiredPrepaymentShare(id, prepaymentShare);
    }
}
