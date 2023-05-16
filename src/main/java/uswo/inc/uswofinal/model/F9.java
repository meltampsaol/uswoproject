package uswo.inc.uswofinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "f9")
public class F9 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recid;

    private Integer lcode;
    private Integer dcode;
    private String wkno;
    private Double f9;
    private Double thdistrict;
    private Double lingap;
    private Double central;
    private Double thlokal;

    // Constructors, getters, and setters

    public F9() {
    }

    public F9(Integer lcode, Integer dcode, String wkno, Double f9, Double thdistrict, Double lingap, Double central, Double thlokal) {
        this.lcode = lcode;
        this.dcode = dcode;
        this.wkno = wkno;
        this.f9 = f9;
        this.thdistrict = thdistrict;
        this.lingap = lingap;
        this.central = central;
        this.thlokal = thlokal;
    }

    public Integer getRecid() {
        return recid;
    }

    public void setRecid(Integer recid) {
        this.recid = recid;
    }

    public Integer getLcode() {
        return lcode;
    }

    public void setLcode(Integer lcode) {
        this.lcode = lcode;
    }

    public Integer getDcode() {
        return dcode;
    }

    public void setDcode(Integer dcode) {
        this.dcode = dcode;
    }

    public String getWkno() {
        return wkno;
    }

    public void setWkno(String wkno) {
        this.wkno = wkno;
    }

    public Double getF9() {
        return f9;
    }

    public void setF9(Double f9) {
        this.f9 = f9;
    }

    public Double getThdistrict() {
        return thdistrict;
    }

    public void setThdistrict(Double thdistrict) {
        this.thdistrict = thdistrict;
    }

    public Double getLingap() {
        return lingap;
    }

    public void setLingap(Double lingap) {
        this.lingap = lingap;
    }

    public Double getCentral() {
        return central;
    }

    public void setCentral(Double central) {
        this.central = central;
    }

    public Double getThlokal() {
        return thlokal;
    }

    public void setThlokal(Double thlokal) {
        this.thlokal = thlokal;
    }
}

