package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import uswo.inc.uswofinal.model.District;


import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    

    District findByDcode(String dcode);

    District findBydid(Integer did);

    @Query("SELECT new District(d.district, d.did) FROM District d")
    List<District> findAllDistricts();

}
