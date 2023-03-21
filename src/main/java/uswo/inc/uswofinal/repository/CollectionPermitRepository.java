package uswo.inc.uswofinal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uswo.inc.uswofinal.model.CollectionPermit;

@Repository
public interface CollectionPermitRepository extends JpaRepository<CollectionPermit, Integer> {
    CollectionPermit findByLcode(Integer lcode);
}

