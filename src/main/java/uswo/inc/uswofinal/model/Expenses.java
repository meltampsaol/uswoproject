package uswo.inc.uswofinal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int recid;

    @ManyToOne
    @JoinColumn(name = "lcode", referencedColumnName = "lcode")
    private Lokal lokal;

    @ManyToOne
    @JoinColumn(name = "did", referencedColumnName = "did")
    private District district;

    @Column(name = "wkno")
    private String wkno;

    @Column(name = "description")
    private String description;

    @Column(name = "f10")
    private String f10;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date_purchased")
    private LocalDate datePurchased;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "qty")
    private double qty;

    @Column(name = "exptype")
    private String exptype;

    public int getRecid() {
        return recid;
    }

    public void setRecid(int recid) {
        this.recid = recid;
    }

    public Lokal getLokal() {
        return lokal;
    }

    public void setLokal(Lokal lokal) {
        this.lokal = lokal;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getWkno() {
        return wkno;
    }

    public void setWkno(String wkno) {
        this.wkno = wkno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getF10() {
        return f10;
    }

    public void setF10(String f10) {
        this.f10 = f10;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getExptype() {
        return exptype;
    }

    public void setExptype(String exptype) {
        this.exptype = exptype;
    }

    
}
