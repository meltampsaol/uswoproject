package uswo.inc.uswofinal.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import uswo.inc.uswofinal.app.CustomLocalDateDeserializer;

@Entity
@Table(name = "expenses")
public class Expense {
    
    @Column(name = "date_encoded")
    private Date dateEncoded;

    @PrePersist
    protected void onCreate() {
        dateEncoded = new Date();
    }

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

    @Column(name = "amount_requested")
    private double amountRequested;

    @Column(name = "actual_expenses")
    private double actualExpenses;

    @Column(name = "date_reported")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
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

   

    public double getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public double getActualExpenses() {
        return actualExpenses;
    }

    public void setActualExpenses(double actualExpenses) {
        this.actualExpenses = actualExpenses;
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

    public static Expense fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Expense expense = null;
        try {
            expense = objectMapper.readValue(json, Expense.class);
            expense.setLokal(Lokal.fromString(expense.getLokal().getLcode().toString()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return expense;
    }

    public Expense(String jsonString) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Expense expense = objectMapper.readValue(jsonString, Expense.class);
        this.setRecid(expense.getRecid());
        this.setLokal(expense.getLokal());
        this.setDistrict(expense.getDistrict());
        this.setWkno(expense.getWkno());
        this.setDescription(expense.getDescription());
        this.setF10(expense.getF10());
        this.setAmountRequested(expense.getAmountRequested());
        this.setActualExpenses(expense.getActualExpenses());
        this.setDatePurchased(expense.getDatePurchased());
        this.setRemarks(expense.getRemarks());
        this.setQty(expense.getQty());
        this.setExptype(expense.getExptype());
    }

    public Expense() {
    }
    

    
}
