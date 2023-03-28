package uswo.inc.uswofinal.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uswo.inc.uswofinal.model.FundReleaseRequest;

@Repository
public interface FundReleaseRequestRepository extends JpaRepository<FundReleaseRequest, Long> {
    FundReleaseRequest findByLcodeAndDidAndApprovalNumber(Integer lcode, Integer did, String approvalNumber);
    FundReleaseRequest findByApprovalNumber(String approvalNumber);
    FundReleaseRequest findByApprovalNumberContainingOrLokalContainingOrParticularsContaining(String approvalNumber, String lokal, String particular);
    List<FundReleaseRequest> findByApprovalNumberContaining(String approvalNumber);
    List<FundReleaseRequest> findByParticularsContaining(String particulars);
    List<FundReleaseRequest> findByLokalContaining(String lokal);
    List<FundReleaseRequest> findByParticulars(String particulars);
   

    @Query("SELECT f FROM FundReleaseRequest f " +
       "JOIN f.lokal l JOIN f.district d " +
       "WHERE f.approvalNumber LIKE %:search% " +
       "OR l.locale LIKE %:search% " +
       "OR d.district LIKE %:search% " +
       "OR f.particulars LIKE %:search%")
List<FundReleaseRequest> searchRequests(@Param("search") String search);
FundReleaseRequest findById(Integer id);



}
