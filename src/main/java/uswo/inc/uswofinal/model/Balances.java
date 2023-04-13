package uswo.inc.uswofinal.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "temp_balances")
public class Balances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recid;

    private String lokal;

    private String district;

    private Integer foryear;

    private BigDecimal startingBalance;

    private BigDecimal currentPayment;

    private BigDecimal currentBalance;

    public String getLokal() {
        return lokal;
    }

    public void setLokal(String lokal) {
        this.lokal = lokal;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    
    public Integer getForyear() {
        return foryear;
    }

    public void setForyear(Integer foryear) {
        this.foryear = foryear;
    }

    public BigDecimal getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(BigDecimal startingBalance) {
        this.startingBalance = startingBalance;
    }

    public BigDecimal getCurrentPayment() {
        return currentPayment;
    }

    public void setCurrentPayment(BigDecimal currentPayment) {
        this.currentPayment = currentPayment;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    

    // getters and setters
    
}
