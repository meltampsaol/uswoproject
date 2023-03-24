package uswo.inc.uswofinal.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uswo.inc.uswofinal.model.FundReleaseRequest;

@Repository
public interface FundReleaseRequestRepository extends JpaRepository<FundReleaseRequest, Long> {
    FundReleaseRequest findByLcodeAndDidAndApprovalNumber(Integer lcode, Integer did, String approvalNumber);
    FundReleaseRequest findByApprovalNumber(String approvalNumber);
    List<FundReleaseRequest> findByApprovalNumberContainingOrLokalContainingOrParticularsContaining(String approvalNumber, String lokal, String particular);
    List<FundReleaseRequest> findByApprovalNumberContaining(String approvalNumber);
    List<FundReleaseRequest> findByParticularsContaining(String particulars);
    List<FundReleaseRequest> findByLokalContaining(String lokal);
    List<FundReleaseRequest> findByParticulars(String particulars);
}
