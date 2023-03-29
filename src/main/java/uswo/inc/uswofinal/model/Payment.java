package uswo.inc.uswofinal.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lcode")
    private Lokal lokal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private District district;

    private String wkno;

    private BigDecimal amount;

    private String f2bnumber;

    private LocalDate date;

    private Integer userid;

    private Integer deleted;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String project;
    private String foryear;

    // getters and setters
}
