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

    @Query("SELECT l FROM Lokal l WHERE l.district.did = :did ORDER BY l.locale")
    List<Lokal> findByDistrict(@Param("did") Integer did);

    @Query("SELECT l FROM Lokal l WHERE l.lcode = :lcode")
    Lokal findByLokalCode(@Param("lcode") Integer lcode);

    
}
