package uswo.inc.uswofinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "wlfr")
@Data
public class Wlfr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recid;
    
    @Transient
    private String lokal;
    
    @Transient
    private String district;

    private Integer did;
    private Integer lcode;
    
    private String wkno;
    
    private Double bankstart;
    
    private Double interest;
    
    private Double retamount;
    
    private Double miscin;
    
    private Double bankcharge;
    
    private Double miscout;
    
    private Double bank_balance;
    
    private Double lfstart;
    
    private Double lfremit;
    
    private Double lfappf10;
    
    private Double lfmiscout;
    
    private Double lf_balance;
    
    private Double cfstart;
    
    private Double cfremit;
    
    private Double cfappf10;

    private Double cfmiscout;
    
    private Double cfintl;
    
    private Double cf_balance;
    
    private Double amtwithheld;

    private Double resumen;
    
    
}
