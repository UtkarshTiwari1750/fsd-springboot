package payment_gateway.demo.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BANK_TRANSFER")
public class BankTransferPayment extends BasePayment {

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "routing_number")
    private String routingNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Override
    public String getPaymentMethod() {
        return "BANK_TRANSFER";
    }

    @Override
    public boolean processPayment() {
        if (!validatePayment()) {
            setStatus("FAILED");
            return false;
        }
        
        try {
            setStatus("PROCESSING");
            if (!verifyBankDetails()) {
                setStatus("FAILED");
                return false;
            }
            
            // Simulate bank transfer processing (longer time)
            Thread.sleep(2000);
            setStatus("COMPLETED");
            return true;
        } catch (Exception e) {
            setStatus("FAILED");
            return false;
        }
    }

    @Override
    public boolean validatePayment() {
        return verifyBankDetails() && 
               getAmount() != null && getAmount() > 0 &&
               getTransactionId() != null && !getTransactionId().isEmpty();
    }

    public boolean verifyBankDetails() {
        return accountNumber != null && accountNumber.length() >= 8 && accountNumber.length() <= 17 &&
               routingNumber != null && routingNumber.length() == 9 &&
               bankName != null && !bankName.isEmpty() &&
               accountHolderName != null && !accountHolderName.isEmpty();
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}
