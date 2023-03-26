package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

  @Query("SELECT e FROM Expense e WHERE " +
          "LOWER(e.lokal) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
          "LOWER(e.district) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
          "LOWER(e.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
          "e.amountRequested = :searchText OR " +
          "e.actualExpenses = :searchText OR " +
          "e.f10 = :searchText OR " +
          "LOWER(e.remarks) LIKE LOWER(CONCAT('%', :searchText, '%'))")
  List<Expense> search(String searchText);

}


