package uswo.inc.uswofinal.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uswo.inc.uswofinal.model.FundReleaseRequest;

@Repository
public interface FundReleaseRequestRepository extends JpaRepository<FundReleaseRequest, Long> {
    FundReleaseRequest findByLcodeAndDidAndApprovalNumber(Integer lcode, Integer did, String approvalNumber);
   
}

