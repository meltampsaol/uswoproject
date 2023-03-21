package uswo.inc.uswofinal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "lokal")
public class Lokal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lcode;

    private String locale;
 
    @Column(name = "did")
    private Integer did;

    @Column(name = "wscount")
    private Integer wscount;

  
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "did", referencedColumnName = "did", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private District district;

    
    // getters and setters
}
