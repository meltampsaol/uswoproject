package uswo.inc.uswofinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "collection_permit")
public class CollectionPermit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recid")
    private Integer id;
    
    @Column(name = "lcode")
    private Integer lcode;
    
    @Column(name = "dcode")
    private Integer dcode;
    
    @Column(name = "permitnumber_district")
    private String permitnumber_district;
    
    @Column(name = "permitnumber_locale")
    private String permitnumber_locale;
    
    @Column(name = "locale")
    private BigDecimal locale;
    
    @Column(name = "district")
    private BigDecimal district;
    
    @Column(name = "lingap")
    private BigDecimal lingap;

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

    public String getPermitnumber_district() {
        return permitnumber_district;
    }

    public void setPermitnumber_district(String permitnumber_district) {
        this.permitnumber_district = permitnumber_district;
    }

    public String getPermitnumber_locale() {
        return permitnumber_locale;
    }

    public void setPermitnumber_locale(String permitnumber_locale) {
        this.permitnumber_locale = permitnumber_locale;
    }

    public BigDecimal getLocale() {
        return locale;
    }

    public void setLocale(BigDecimal locale) {
        this.locale = locale;
    }

    public BigDecimal getDistrict() {
        return district;
    }

    public void setDistrict(BigDecimal district) {
        this.district = district;
    }

    public BigDecimal getLingap() {
        return lingap;
    }

    public void setLingap(BigDecimal lingap) {
        this.lingap = lingap;
    }
    
    // getters and setters
    
}
