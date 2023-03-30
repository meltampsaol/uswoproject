package uswo.inc.uswofinal.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Query("SELECT s FROM Subscription s WHERE s.lokal.locale LIKE %:searchTerm% OR s.district.district LIKE %:searchTerm% OR s.foryear = :value OR s.balance = :value")
    List<Subscription> search(@Param("searchTerm") String searchTerm, @Param("value") BigDecimal value);

   Subscription findByLokalAndForyear(Lokal lokal, int foryear);

}

