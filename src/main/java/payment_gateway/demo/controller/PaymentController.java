
// PaymentController.java
package payment_gateway.demo.controller;

import payment_gateway.demo.entity.BankTransferPayment;
import payment_gateway.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add/{userId}")
    public BankTransferPayment addPayment(@PathVariable Long userId, @RequestBody BankTransferPayment payment) {
        return paymentService.createPayment(userId, payment);
    }

    @GetMapping("/user/{userId}")
    public List<BankTransferPayment> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }
}
