package payment_gateway.demo.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardPayment extends BasePayment {

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Override
    public String getPaymentMethod() {
        return "CREDIT_CARD";
    }

    @Override
    public boolean processPayment() {
        if (!validatePayment()) {
            setStatus("FAILED");
            return false;
        }
        
        try {
            setStatus("PROCESSING");
            // Simulate credit card processing
            Thread.sleep(1000);
            setStatus("COMPLETED");
            return true;
        } catch (Exception e) {
            setStatus("FAILED");
            return false;
        }
    }

    @Override
    public boolean validatePayment() {
        return validateCardDetails() && 
               getAmount() != null && getAmount() > 0 &&
               getTransactionId() != null && !getTransactionId().isEmpty();
    }

    public boolean validateCardDetails() {
        return cardNumber != null && cardNumber.length() >= 13 && cardNumber.length() <= 19 &&
               expiryDate != null && !expiryDate.isEmpty() &&
               cvv != null && cvv.length() >= 3 && cvv.length() <= 4 &&
               cardHolderName != null && !cardHolderName.isEmpty();
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
