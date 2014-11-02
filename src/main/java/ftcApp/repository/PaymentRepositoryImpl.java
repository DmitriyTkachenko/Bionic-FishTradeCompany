package ftcApp.repository;

import ftcApp.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl extends GenericRepositoryImpl<Payment, Integer> implements PaymentRepository {
    public PaymentRepositoryImpl() { super(Payment.class); }
}
