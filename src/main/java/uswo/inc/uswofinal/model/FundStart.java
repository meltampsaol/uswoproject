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
@Table(name = "fundstart")
@Data
public class FundStart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer lcode;

    private Integer dcode;

    private String wkno;

    private BigDecimal lingap;

    @Column(name = "f13locale")
    private BigDecimal f13Locale;

    @Column(name = "f13district")
    private BigDecimal f13District;

    private BigDecimal f9;

    @Column(name = "bank")
    private BigDecimal bank;

    @Column(name = "usmo")
    private BigDecimal usmo;

    @Column(name = "cfo")
    private BigDecimal cfo;
    

}
