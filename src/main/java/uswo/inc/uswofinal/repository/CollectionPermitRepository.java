package uswo.inc.uswofinal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uswo.inc.uswofinal.model.CollectionPermit;


public interface CollectionPermitRepository extends JpaRepository<CollectionPermit, Integer> {
    CollectionPermit findByLcode(Integer lcode);
}

