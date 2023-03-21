package uswo.inc.uswofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uswo.inc.uswofinal.model.UserInfo;


import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsername(String username);
}
