
// PaymentService.java
package payment_gateway.demo.service;

import payment_gateway.demo.entity.Payment;
import payment_gateway.demo.entity.User;
import payment_gateway.demo.repository.PaymentRepository;
import payment_gateway.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment createPayment(Long userId, Payment payment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        payment.setUser(user);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
}
