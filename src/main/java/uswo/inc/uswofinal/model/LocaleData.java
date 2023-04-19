package uswo.inc.uswofinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "remittance")
public class LocaleData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "did")
    private Integer did;
   
    @Column(name = "wkno")
    private String wkno;

    @Column(name = "locale")
    private String locale;

    @Column(name = "thursday")
    private Double thursday;

    @Column(name = "sunday")
    private Double sunday;

    @Column(name = "cws")
    private Double cws;

    @Column(name = "totaloffering")
    private Double totalOffering;

    @Column(name = "thlocale")
    private Double thLocale;

    @Column(name = "thdistrict")
    private Double thDistrict;

    @Column(name = "lingap")
    private Double lingap;

    @Column(name = "thanksgiving")
    private Double thanksgiving;

    @Column(name = "reflocale")
    private Double refLocale;

    @Column(name = "refdistrict")
    private Double refDistrict;

    @Column(name = "refcentral")
    private Double refCentral;

    @Column(name = "offrefund")
    private Double offRefund;

    @Column(name = "alms")
    private Double alms;

    @Column(name = "explocale")
    private Double expLocale;

    @Column(name = "expdistrict")
    private Double expDistrict;

    @Column(name = "expcentral")
    private Double expCentral;

    @Column(name = "exptotal")
    private Double expTotal;

    @Column(name = "remainder")
    private Double remainder;

    @Column(name = "uswo")
    private Double uswo;

    @Column(name = "cfototal")
    private Double cfoTotal;

    @Column(name = "cfolocale")
    private Double cfoLocale;

    @Column(name = "cfointl")
    private Double cfoIntl;

    @Column(name = "rdistrict")
    private Double rDistrict;

    @Column(name = "rlingap")
    private Double rLingap;

    @Column(name = "rcentral")
    private Double rCentral;

    @Column(name = "rtotal")
    private Double rTotal;

    @Column(name = "lcode")
    private Integer lcode;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getWkno() {
        return wkno;
    }

    public void setWkno(String wkno) {
        this.wkno = wkno;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getThursday() {
        return thursday;
    }

    public void setThursday(Double thursday) {
        this.thursday = thursday;
    }

    public Double getSunday() {
        return sunday;
    }

    public void setSunday(Double sunday) {
        this.sunday = sunday;
    }

    public Double getCws() {
        return cws;
    }

    public void setCws(Double cws) {
        this.cws = cws;
    }

    public Double getTotalOffering() {
        return totalOffering;
    }

    public void setTotalOffering(Double totalOffering) {
        this.totalOffering = totalOffering;
    }

    public Double getThLocale() {
        return thLocale;
    }

    public void setThLocale(Double thLocale) {
        this.thLocale = thLocale;
    }

    public Double getThDistrict() {
        return thDistrict;
    }

    public void setThDistrict(Double thDistrict) {
        this.thDistrict = thDistrict;
    }

    public Double getLingap() {
        return lingap;
    }

    public void setLingap(Double lingap) {
        this.lingap = lingap;
    }

    public Double getThanksgiving() {
        return thanksgiving;
    }

    public void setThanksgiving(Double thanksgiving) {
        this.thanksgiving = thanksgiving;
    }

    public Double getRefLocale() {
        return refLocale;
    }

    public void setRefLocale(Double refLocale) {
        this.refLocale = refLocale;
    }

    public Double getRefDistrict() {
        return refDistrict;
    }

    public void setRefDistrict(Double refDistrict) {
        this.refDistrict = refDistrict;
    }

    public Double getRefCentral() {
        return refCentral;
    }

    public void setRefCentral(Double refCentral) {
        this.refCentral = refCentral;
    }

    public Double getOffRefund() {
        return offRefund;
    }

    public void setOffRefund(Double offRefund) {
        this.offRefund = offRefund;
    }

    public Double getAlms() {
        return alms;
    }

    public void setAlms(Double alms) {
        this.alms = alms;
    }

    public Double getExpLocale() {
        return expLocale;
    }

    public void setExpLocale(Double expLocale) {
        this.expLocale = expLocale;
    }

    public Double getExpDistrict() {
        return expDistrict;
    }

    public void setExpDistrict(Double expDistrict) {
        this.expDistrict = expDistrict;
    }

    public Double getExpCentral() {
        return expCentral;
    }

    public void setExpCentral(Double expCentral) {
        this.expCentral = expCentral;
    }

    public Double getExpTotal() {
        return expTotal;
    }

    public void setExpTotal(Double expTotal) {
        this.expTotal = expTotal;
    }

    public Double getRemainder() {
        return remainder;
    }

    public void setRemainder(Double remainder) {
        this.remainder = remainder;
    }

    public Double getUswo() {
        return uswo;
    }

    public void setUswo(Double uswo) {
        this.uswo = uswo;
    }

    public Double getCfoTotal() {
        return cfoTotal;
    }

    public void setCfoTotal(Double cfoTotal) {
        this.cfoTotal = cfoTotal;
    }

    public Double getCfoLocale() {
        return cfoLocale;
    }

    public void setCfoLocale(Double cfoLocale) {
        this.cfoLocale = cfoLocale;
    }

    public Double getCfoIntl() {
        return cfoIntl;
    }

    public void setCfoIntl(Double cfoIntl) {
        this.cfoIntl = cfoIntl;
    }

    public Double getrDistrict() {
        return rDistrict;
    }

    public void setrDistrict(Double rDistrict) {
        this.rDistrict = rDistrict;
    }

    public Double getrLingap() {
        return rLingap;
    }

    public void setrLingap(Double rLingap) {
        this.rLingap = rLingap;
    }

    public Double getrCentral() {
        return rCentral;
    }

    public void setrCentral(Double rCentral) {
        this.rCentral = rCentral;
    }

    public Double getrTotal() {
        return rTotal;
    }

    public void setrTotal(Double rTotal) {
        this.rTotal = rTotal;
    }

    public Integer getLcode() {
        return lcode;
    }

    public void setLcode(Integer lcode) {
        this.lcode = lcode;
    }

    
}
