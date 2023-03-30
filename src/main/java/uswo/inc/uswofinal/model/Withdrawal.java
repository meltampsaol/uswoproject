package uswo.inc.uswofinal.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "withdrawal")
@Data
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recid;

    @ManyToOne
    @JoinColumn(name = "lcode")
    private Lokal lokal;

    @ManyToOne
    @JoinColumn(name = "did")
    private District district;

    private String wkno;

    private Double amount;
}
 
