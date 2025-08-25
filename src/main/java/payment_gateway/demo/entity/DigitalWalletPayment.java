package payment_gateway.demo.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DIGITAL_WALLET")
public class DigitalWalletPayment extends BasePayment {

    @Column(name = "wallet_type")
    private String walletType;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Override
    public String getPaymentMethod() {
        return "DIGITAL_WALLET";
    }

    @Override
    public boolean processPayment() {
        if (!validatePayment()) {
            setStatus("FAILED");
            return false;
        }
        
        try {
            setStatus("PROCESSING");
            if (!verifyWallet()) {
                setStatus("FAILED");
                return false;
            }
            
            // Simulate digital wallet processing
            Thread.sleep(800);
            setStatus("COMPLETED");
            return true;
        } catch (Exception e) {
            setStatus("FAILED");
            return false;
        }
    }

    @Override
    public boolean validatePayment() {
        return verifyWallet() && 
               getAmount() != null && getAmount() > 0 &&
               getTransactionId() != null && !getTransactionId().isEmpty();
    }

    public boolean verifyWallet() {
        return walletType != null && !walletType.isEmpty() &&
               walletId != null && !walletId.isEmpty() &&
               phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    // Getters and Setters
    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
