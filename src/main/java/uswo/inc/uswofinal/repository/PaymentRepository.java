package uswo.inc.uswofinal.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uswo.inc.uswofinal.model.Lokal;
import uswo.inc.uswofinal.model.Payment;
import uswo.inc.uswofinal.model.PaymentType;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.lokal.locale LIKE %:searchTerm% OR p.district.district LIKE %:searchTerm% OR p.wkno LIKE %:searchTerm% OR p.amount = :value OR p.paymentType LIKE %:searchTerm%")
    List<Payment> search(@Param("searchTerm") String searchTerm, @Param("value") BigDecimal value);

    // get the most recent expenses (limited to the top 10)
  @Query("SELECT e FROM Payment e ORDER BY e.date_encoded DESC")
  List<Payment> findRecentPayments();

  @Query("SELECT e FROM Payment e WHERE e.lokal.lcode = :lcode ORDER BY e.date_encoded DESC")
  List<Payment> findRecentPerLokal(@Param("lcode") int lcode);


  Payment findById(int id);

  void deleteById(int id);
  
  @Query("SELECT e FROM Payment e " +
  "LEFT JOIN FETCH e.lokal l " +
  "LEFT JOIN FETCH e.district d " +
  "WHERE LOWER(l.locale) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(d.district) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.wkno) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.remarks) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.f2bnumber) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.project) LIKE LOWER(CONCAT('%', :searchText, '%'))")
List<Payment> findBySearchText(@Param("searchText") String searchText);

Payment findByLokalAndWknoAndF2bnumberAndPaymentType(Lokal lokal, String wkno, String f2bnumber,
        PaymentType paymentType);


}

