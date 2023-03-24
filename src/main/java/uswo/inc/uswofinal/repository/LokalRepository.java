package uswo.inc.uswofinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.Lokal;


public interface LokalRepository extends JpaRepository<Lokal, Integer> {
   
    
    List<Lokal> findByDistrictDid(Integer districtId);
    Optional<Lokal> findByLcode(Integer lcode);
    Optional<Lokal> findById(Integer lcode);

   
    @Query("SELECT l FROM Lokal l JOIN FETCH l.district WHERE l.did = :districtId ORDER BY l.locale")
    List<Lokal> findByDistrict(@Param("districtId") Integer districtId);

    @Query("SELECT l FROM Lokal l WHERE l.lcode = :lcode")
    Lokal findByLokalCode(@Param("lcode") Integer lcode);
    
    Lokal findByLocale(String name);

    
}
