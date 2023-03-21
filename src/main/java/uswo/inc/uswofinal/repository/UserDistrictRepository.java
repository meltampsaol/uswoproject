package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uswo.inc.uswofinal.model.UserDistrict;
import uswo.inc.uswofinal.model.UserInfo;

import java.util.List;


public interface UserDistrictRepository extends JpaRepository<UserDistrict, Long> {
    List<UserDistrict> findByUserInfo(UserInfo userInfo);
}
