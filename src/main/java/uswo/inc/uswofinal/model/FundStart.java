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
@Table(name = "wlfr_start")
@Data
public class FundStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recid;

    private Integer lcode;

    private Integer did;

    private String wkno;

    @Column(name = "bankstart")
    private BigDecimal bank;

    @Column(name = "lfstart")
    private BigDecimal usmo;

    @Column(name = "cfstart")
    private BigDecimal cfo;
    

}
