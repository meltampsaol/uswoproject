package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uswo.inc.uswofinal.model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Integer> {
}
