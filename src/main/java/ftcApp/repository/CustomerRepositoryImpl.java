package ftcApp.repository;

import ftcApp.model.Customer;

public class CustomerRepositoryImpl extends UserRepositoryImpl<Customer, Integer> implements CustomerRepository {
    @Override
    public void updateRequiredPrepaymentShare(Integer id, double prepaymentShare) {
        Customer customer = this.findOne(id);
        customer.setPrepaymentShareRequired(prepaymentShare);
        this.save(customer);
    }
}
