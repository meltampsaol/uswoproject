package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uswo.inc.uswofinal.model.Balances;

public interface BalancesRepository extends JpaRepository<Balances, Integer> {

    
    
    
    
    //@Query("SELECT a.lokal.getLocale(), coalesce(a.foryear, 0), a.balance AS startingbalance, SUM(IFNULL(Payment.amount, 0)) AS currentpayment, SUM(a.balance - IFNULL(Payment.amount, 0)) AS currentbalance FROM Subscription a LEFT JOIN Payment ON a.lokal.getLocale() = payment.lokal.getLocale() AND a.district.getDid() = Payment.district.getDid() INNER JOIN Lokal l ON l.locale = a.lokal.getLocale() WHERE l.locale LIKE :locale GROUP BY a.lokal.getLocale(), a.foryear")
    //List<Balances> getBalances2(String locale);

    

    
}
