package uswo.inc.uswofinal.model;

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

    private Double lingap;

    private Double f13;

    private Double f9;

    @Column(name = "bank")
    private Double Bank;

    @Column(name = "usmo")
    private Double Usmo;

    @Column(name = "cfo")
    private Double Cfo;

    
}
