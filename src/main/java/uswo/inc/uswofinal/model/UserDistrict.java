package uswo.inc.uswofinal.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "user_district")
public class UserDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @Column(name = "district_code")
    private String districtCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return userInfo;
    }

    public void setUser(UserInfo user) {
        this.userInfo = user;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
