package uswo.inc.uswofinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uswo.inc.uswofinal.config.UserInfoUserDetails;
import uswo.inc.uswofinal.model.UserInfo;
import uswo.inc.uswofinal.repository.UserInfoRepository;
import java.util.Optional;




@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    
    public UserInfoUserDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository; //this line cause a problem in Security config class
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
