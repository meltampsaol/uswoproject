package uswo.inc.uswofinal.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.lokal.locale LIKE %:searchTerm% OR p.district.district LIKE %:searchTerm% OR p.wkno LIKE %:searchTerm% OR p.amount = :value OR p.paymentType LIKE %:searchTerm%")
    List<Payment> search(@Param("searchTerm") String searchTerm, @Param("value") BigDecimal value);
}

