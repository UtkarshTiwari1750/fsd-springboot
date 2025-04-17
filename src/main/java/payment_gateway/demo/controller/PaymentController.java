
// PaymentController.java
package payment_gateway.demo.controller;

import payment_gateway.demo.entity.Payment;
import payment_gateway.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://fsd-angular.vercel.app")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add/{userId}")
    public Payment addPayment(@PathVariable Long userId, @RequestBody Payment payment) {
        return paymentService.createPayment(userId, payment);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getPaymentsByUser(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }
}
