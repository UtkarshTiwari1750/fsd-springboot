
// PaymentRepository.java
package payment_gateway.demo.repository;

import payment_gateway.demo.entity.BankTransferPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<BankTransferPayment, Long> {
    List<BankTransferPayment> findByUserId(Long userId);
}
