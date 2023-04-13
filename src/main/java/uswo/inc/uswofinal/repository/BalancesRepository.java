package uswo.inc.uswofinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;


import uswo.inc.uswofinal.model.Balances;


public interface BalancesRepository extends JpaRepository<Balances,Integer> {


    @Procedure(procedureName = "get_all_balances")
    List<Balances> GET_ALL_BALANCES(String search_string);

     
    @Query("SELECT a.lokal.lcode, a.foryear, a.balance AS startingbalance, SUM(IFNULL(Payment.amount, 0)) AS currentpayment, SUM(a.balance - IFNULL(Payment.amount, 0)) AS currentbalance FROM Subscription a LEFT JOIN Payment ON a.lokal.lcode = Payment.lokal.lcode AND a.`foryear`=Payment.`foryear` AND a.did = Payment.did INNER JOIN Lokal l ON l.lcode = a.lokal.lcode WHERE l.locale LIKE :locale GROUP BY a.lokal.lcode, a.foryear")
    List<Balances> getBalances2(String locale);
}