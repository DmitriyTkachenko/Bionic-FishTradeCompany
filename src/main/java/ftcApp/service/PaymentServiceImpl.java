package ftcApp.service;

import ftcApp.model.Payment;
import ftcApp.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer> implements PaymentService {
    @Inject
    PaymentServiceImpl(PaymentRepository repository) {
        super(repository, Payment.class);
    }
}
