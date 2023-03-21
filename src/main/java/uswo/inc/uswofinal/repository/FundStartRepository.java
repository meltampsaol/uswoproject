package uswo.inc.uswofinal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uswo.inc.uswofinal.model.FundStart;

@Repository
public interface FundStartRepository extends JpaRepository<FundStart, Integer> {
    FundStart findByLcode(Integer lcode);
    FundStart findByLcodeAndDcode(Integer lcode, Integer dcode);
}
