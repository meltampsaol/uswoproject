package uswo.inc.uswofinal.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "f9")
@Data
public class F9Plus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recid;

    private Integer lcode;

    private Integer dcode;

    private String wkno;

    @Column(name = "lokal")
    private BigDecimal lokal;

    @Column(name = "district")
    private BigDecimal district;

    private BigDecimal f9;

    @Column(name = "central")
    private BigDecimal central;

    @Column(name = "lingap")
    private BigDecimal lingap;

    
    

}
