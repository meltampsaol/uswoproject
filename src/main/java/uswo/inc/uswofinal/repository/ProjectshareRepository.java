package uswo.inc.uswofinal.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.AccountType;
import uswo.inc.uswofinal.model.Projectshare;

public interface ProjectshareRepository extends JpaRepository<Projectshare, Long> {
    @Query("SELECT p FROM Projectshare p WHERE p.lokal.locale LIKE %:searchTerm% OR p.district.district LIKE %:searchTerm% OR p.item LIKE %:searchTerm% OR p.share = :value OR p.account = :accountType")
    List<Projectshare> search(@Param("searchTerm") String searchTerm, @Param("value") BigDecimal value, @Param("accountType") AccountType accountType);
}

