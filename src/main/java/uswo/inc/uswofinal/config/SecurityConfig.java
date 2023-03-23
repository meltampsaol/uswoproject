package uswo.inc.uswofinal.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import uswo.inc.uswofinal.repository.UserInfoRepository;
import uswo.inc.uswofinal.service.UserInfoUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
    return new HandlerMappingIntrospector();
} 

 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            return http.authorizeHttpRequests()
                    .requestMatchers("/", "/all","/create-user","/save-user","/api/getlocales2/*","/getlocales/*","/f1control","/save-ws","/getlocales3/*").permitAll()
                    .requestMatchers("/lokal-list/*","/update-lokal/*","/lokal/*","/updateLokal/*","/f1generator/*","/fieldgenerator/*").permitAll()
                    .requestMatchers("/f1auditor/*","/mynote","/addnote","/lokal-list-fundstart/*","/fundstart").permitAll()
                    .requestMatchers("/savefundstart/*","/fundstartlist","/collectionpermit","/lokal-list-permit/*","/savepermit/*").permitAll()
                    .requestMatchers("/addnote","/update-note/*","/updatenote/*","/update-note/","/addnote/").permitAll()
                    .requestMatchers("/home").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/home") 
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/landing")
                    .permitAll()
                    .and()
                    .csrf().disable()
                    .build();
       
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(UserInfoRepository userInfoRepository) {
        return new UserInfoUserDetailsService(userInfoRepository);
    }

}
