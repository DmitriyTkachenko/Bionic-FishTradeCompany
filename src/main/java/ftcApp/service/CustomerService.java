package ftcApp.service;

import ftcApp.model.Customer;

public interface CustomerService extends GenericService<Customer, Integer> {
    void updateRequiredPrepaymentShare(Integer id, double prepaymentShare);
}
