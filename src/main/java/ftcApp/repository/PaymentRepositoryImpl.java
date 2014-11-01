package ftcApp.repository;

import ftcApp.model.Payment;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class PaymentRepositoryImpl extends RepositoryImpl<Payment, Integer> implements PaymentRepository {
    public PaymentRepositoryImpl() { super(Payment.class); }
}
