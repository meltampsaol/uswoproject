package uswo.inc.uswofinal.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.GeneratedValue;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer did;

    private String dcode;

    private String district;

    @JsonIgnore
    @OneToMany(mappedBy = "district", fetch = FetchType.EAGER)
    private List<Lokal> locales;

    public District(String district, Integer did) {
        this.district = district;
        this.did = did;
    }

    public District() {
    }

    // getters and setters

    @Override
    public String toString() {
        return "District{" +
                "did=" + did +
                ", dcode='" + dcode + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
