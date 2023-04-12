package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uswo.inc.uswofinal.model.F4Detail;

@Repository
public interface F4DetailRepository extends JpaRepository<F4Detail, Integer> {
    List<F4Detail> findByWkno(String wkno);
    List<F4Detail> findByWknoAndDid(String wkno, int did);
}
