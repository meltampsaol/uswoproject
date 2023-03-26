package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import uswo.inc.uswofinal.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  // get a list of expenses sorted by date in descending order
  List<Expense> findAllByOrderByDateEncodedDesc();

  // get the most recent expenses (limited to the top 10)
  @Query("SELECT e FROM Expense e ORDER BY e.dateEncoded DESC")
  List<Expense> findRecentExpenses();

  Expense findById(long id);

  void deleteById(long id);
  @Query("SELECT e FROM Expense e " +
  "LEFT JOIN FETCH e.lokal l " +
  "LEFT JOIN FETCH e.district d " +
  "WHERE LOWER(l.locale) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(d.district) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.f10) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
  "OR LOWER(e.remarks) LIKE LOWER(CONCAT('%', :searchText, '%'))")
List<Expense> findBySearchText(@Param("searchText") String searchText);





}


