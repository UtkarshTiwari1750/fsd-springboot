
// PaymentService.java
package payment_gateway.demo.service;

import payment_gateway.demo.entity.BankTransferPayment;
import payment_gateway.demo.entity.BankTransferPayment;
// import payment_gateway.demo.entity.BankTransferPayment;
import payment_gateway.demo.entity.User;
import payment_gateway.demo.repository.PaymentRepository;
import payment_gateway.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentFactory paymentFactory;

    public BankTransferPayment createPayment(Long userId, BankTransferPayment payment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (payment.getTransactionId() == null) {
            payment.setTransactionId(UUID.randomUUID().toString());
        }
        
        payment.setUser(user);
        payment.setStatus("PENDING");
        
        BankTransferPayment savedPayment = paymentRepository.save(payment);
        
        if (savedPayment.processPayment()) {
            return paymentRepository.save(savedPayment);
        } else {
            return savedPayment;
        }
    }

    public List<BankTransferPayment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
    
    public BankTransferPayment processPayment(Long paymentId) {
        BankTransferPayment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        if (payment.processPayment()) {
            return paymentRepository.save(payment);
        } else {
            return payment;
        }
    }
}
