package payment_gateway.demo.service;

import payment_gateway.demo.entity.*;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {

    public BasePayment createPayment(String paymentType) {
        switch (paymentType.toUpperCase()) {
            case "CREDIT_CARD":
                return new CreditCardPayment();
            case "DEBIT_CARD":
                return new DebitCardPayment();
            case "DIGITAL_WALLET":
                return new DigitalWalletPayment();
            case "BANK_TRANSFER":
                return new BankTransferPayment();
            default:
                throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
    }
    
    public boolean isValidPaymentType(String paymentType) {
        return paymentType != null && 
               (paymentType.equalsIgnoreCase("CREDIT_CARD") ||
                paymentType.equalsIgnoreCase("DEBIT_CARD") ||
                paymentType.equalsIgnoreCase("DIGITAL_WALLET") ||
                paymentType.equalsIgnoreCase("BANK_TRANSFER"));
    }
}
