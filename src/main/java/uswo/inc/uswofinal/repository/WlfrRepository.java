package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import uswo.inc.uswofinal.model.Wlfr;

@Repository
public interface WlfrRepository extends JpaRepository<Wlfr, Integer> {
    @Transactional
    @Procedure(name = "wlfrAccumulatedBalance2")
    List<Wlfr> wlfrAccumulatedBalance2(Integer xlcode, Integer xdcode);
}
