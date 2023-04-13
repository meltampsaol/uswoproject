package uswo.inc.uswofinal.model;

import java.math.BigDecimal;

public class SubscriptionBalance {
    private Integer lcode;
    private Integer foryear;
    private BigDecimal balance;
    private BigDecimal totalPayment;
    public Integer getLcode() {
        return lcode;
    }
    public void setLcode(Integer lcode) {
        this.lcode = lcode;
    }
    public Integer getForyear() {
        return foryear;
    }
    public void setForyear(Integer foryear) {
        this.foryear = foryear;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getTotalPayment() {
        return totalPayment;
    }
    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    
}

