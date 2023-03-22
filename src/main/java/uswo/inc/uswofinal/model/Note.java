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
@Table(name = "mynote")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recid")
    private Integer id;

    @Column(name = "lcode")
    public Integer lcode;

    @Column(name = "locale")
    public String locale;

    @ManyToOne
    @JoinColumn(name = "did", referencedColumnName = "did")
    public District district;

    @Column(name = "wkno")
    public String wkno;

    @Column(name = "concerns", columnDefinition = "TEXT")
    public String concerns;

    @Column(name = "action", columnDefinition = "TEXT")
    public String action;

    @Column(name = "action_date")
    public LocalDate actionDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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

    public String getConcerns() {
        return concerns;
    }

    public void setConcerns(String concerns) {
        this.concerns = concerns;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Integer getLcode() {
        return lcode;
    }

    public void setLcode(Integer lcode) {
        this.lcode = lcode;
    }

    // Constructors, Getters, Setters, and toString() method omitted for brevity

}
