package uswo.inc.uswofinal.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Table(name="users")
public class UserInfo
{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    private String username;
    private String password;
    private String roles;

    public UserInfo(){}
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true) private Set<UserDistrict> userDistricts = new HashSet<>();

    public UserInfo(Long id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
