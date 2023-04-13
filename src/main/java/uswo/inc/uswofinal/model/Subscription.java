package uswo.inc.uswofinal.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscription")
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recid;

    @ManyToOne
    @JoinColumn(name = "lcode", referencedColumnName = "lcode")
    private Lokal lokal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "did")
    private District district;

    private Integer foryear;

    private BigDecimal balance;
}

