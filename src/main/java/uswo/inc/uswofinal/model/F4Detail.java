package uswo.inc.uswofinal.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "f4detail")
public class F4Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recid;

    @ManyToOne
    @JoinColumn(name = "lcode", referencedColumnName = "lcode")
    private Lokal lokal;

    private BigDecimal thursday;
    private BigDecimal sunday;
    private BigDecimal cws;
    private BigDecimal totaloffering;
    private BigDecimal thlocale;
    private BigDecimal thdistrict;
    private BigDecimal lingap;
    private BigDecimal thanksgiving;
    private BigDecimal reflocale;
    private BigDecimal refdistrict;
    private BigDecimal refcentral;
    private BigDecimal offrefund;
    private BigDecimal alms;
    private BigDecimal explocale;
    private BigDecimal expdistrict;
    private BigDecimal expcentral;
    private BigDecimal exptotal;
    private BigDecimal remainder;
    private BigDecimal uswo;
    private BigDecimal cfototal;
    private BigDecimal cfolocale;
    private BigDecimal cfointl;
    private BigDecimal rdistrict;
    private BigDecimal rlingap;
    private BigDecimal rcentral;
    private BigDecimal rtotal;
  
    @ManyToOne
    @JoinColumn(name = "did", referencedColumnName = "did")
    private District district;
    
    private String wkno;
    private String chkno;
    private String reported;
   
    public String getChkno() {
        return chkno;
    }
    public void setChkno(String chkno) {
        this.chkno = chkno;
    }
    public BigDecimal getThursday() {
        return thursday;
    }
    public void setThursday(BigDecimal thursday) {
        this.thursday = thursday;
    }
    public BigDecimal getSunday() {
        return sunday;
    }
    public void setSunday(BigDecimal sunday) {
        this.sunday = sunday;
    }
    public BigDecimal getCws() {
        return cws;
    }
    public void setCws(BigDecimal cws) {
        this.cws = cws;
    }
    public BigDecimal getTotaloffering() {
        return totaloffering;
    }
    public void setTotaloffering(BigDecimal totaloffering) {
        this.totaloffering = totaloffering;
    }
    public BigDecimal getThlocale() {
        return thlocale;
    }
    public void setThlocale(BigDecimal thlocale) {
        this.thlocale = thlocale;
    }
    public BigDecimal getThdistrict() {
        return thdistrict;
    }
    public void setThdistrict(BigDecimal thdistrict) {
        this.thdistrict = thdistrict;
    }
    public BigDecimal getLingap() {
        return lingap;
    }
    public void setLingap(BigDecimal lingap) {
        this.lingap = lingap;
    }
    public BigDecimal getThanksgiving() {
        return thanksgiving;
    }
    public void setThanksgiving(BigDecimal thanksgiving) {
        this.thanksgiving = thanksgiving;
    }
    public BigDecimal getReflocale() {
        return reflocale;
    }
    public void setReflocale(BigDecimal reflocale) {
        this.reflocale = reflocale;
    }
    public BigDecimal getRefdistrict() {
        return refdistrict;
    }
    public void setRefdistrict(BigDecimal refdistrict) {
        this.refdistrict = refdistrict;
    }
    public BigDecimal getRefcentral() {
        return refcentral;
    }
    public void setRefcentral(BigDecimal refcentral) {
        this.refcentral = refcentral;
    }
    public BigDecimal getOffrefund() {
        return offrefund;
    }
    public void setOffrefund(BigDecimal offrefund) {
        this.offrefund = offrefund;
    }
    public BigDecimal getAlms() {
        return alms;
    }
    public void setAlms(BigDecimal alms) {
        this.alms = alms;
    }
    public BigDecimal getExplocale() {
        return explocale;
    }
    public void setExplocale(BigDecimal explocale) {
        this.explocale = explocale;
    }
    public BigDecimal getExpdistrict() {
        return expdistrict;
    }
    public void setExpdistrict(BigDecimal expdistrict) {
        this.expdistrict = expdistrict;
    }
    public BigDecimal getExpcentral() {
        return expcentral;
    }
    public void setExpcentral(BigDecimal expcentral) {
        this.expcentral = expcentral;
    }
    public BigDecimal getExptotal() {
        return exptotal;
    }
    public void setExptotal(BigDecimal exptotal) {
        this.exptotal = exptotal;
    }
    public BigDecimal getRemainder() {
        return remainder;
    }
    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }
    public BigDecimal getUswo() {
        return uswo;
    }
    public void setUswo(BigDecimal uswo) {
        this.uswo = uswo;
    }
    public BigDecimal getCfototal() {
        return cfototal;
    }
    public void setCfototal(BigDecimal cfototal) {
        this.cfototal = cfototal;
    }
    public BigDecimal getCfolocale() {
        return cfolocale;
    }
    public void setCfolocale(BigDecimal cfolocale) {
        this.cfolocale = cfolocale;
    }
    public BigDecimal getCfointl() {
        return cfointl;
    }
    public void setCfointl(BigDecimal cfointl) {
        this.cfointl = cfointl;
    }
    public BigDecimal getRdistrict() {
        return rdistrict;
    }
    public void setRdistrict(BigDecimal rdistrict) {
        this.rdistrict = rdistrict;
    }
    public BigDecimal getRlingap() {
        return rlingap;
    }
    public void setRlingap(BigDecimal rlingap) {
        this.rlingap = rlingap;
    }
    public BigDecimal getRcentral() {
        return rcentral;
    }
    public void setRcentral(BigDecimal rcentral) {
        this.rcentral = rcentral;
    }
    public BigDecimal getRtotal() {
        return rtotal;
    }
    public void setRtotal(BigDecimal rtotal) {
        this.rtotal = rtotal;
    }
    
    public String getWkno() {
        return wkno;
    }
    public void setWkno(String wkno) {
        this.wkno = wkno;
    }
    public String getReported() {
        return reported;
    }
    public void setReported(String reported) {
        this.reported = reported;
    }
    public District getDistrict() {
        return district;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public Lokal getLokal() {
        return lokal;
    }
    public void setLokal(Lokal lokal) {
        this.lokal = lokal;
    }
    public void setLokal(String string) {
    }
    public void setDistrict(String string) {
    }

    
}

