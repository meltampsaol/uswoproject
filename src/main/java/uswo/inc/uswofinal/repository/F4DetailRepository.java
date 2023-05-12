package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.F4Detail;

public interface F4DetailRepository extends JpaRepository<F4Detail, Integer> {
    
    List<F4Detail> findByWkno(String wkno);
    List<F4Detail> findByWknoAndDistrict(String wkno, String district);
    @Query("SELECT f FROM F4Detail f WHERE f.lokal.locale LIKE %:locale% order by f.wkno")
    List<F4Detail> findByLocale(@Param("locale") String lokal);
    @Query("SELECT f FROM F4Detail f JOIN f.district d WHERE d.district = :districtName")
    List<F4Detail> findByDistrict(String districtName);
    List<F4Detail> findByReported(String wkno);

    @Query("SELECT f FROM F4Detail f JOIN f.district d WHERE d.district = :districtName and f.reported = :wkno")
    List<F4Detail> findByDistrictReported(String districtName, String wkno);
}
