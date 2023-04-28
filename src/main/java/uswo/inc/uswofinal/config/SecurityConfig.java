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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers("/", "/all", "/create-user", "/save-user", "/api/getlocales2/*", "/getlocales/*",
                        "/f1control", "/save-ws", "/getlocales3/*")
                .permitAll()
                .requestMatchers("/lokal-list/*", "/update-lokal/*", "/lokal/*", "/updateLokal/*", "/f1generator/*",
                        "/fieldgenerator/*")
                .permitAll()
                .requestMatchers("/f1auditor/*", "/lokal-list-fundstart/*", "/fundstart")
                .permitAll()
                .requestMatchers("/savefundstart/*", "/fundstartlist", "/collectionpermit", "/lokal-list-permit/*","/savepermit/*")
                .permitAll()
                .requestMatchers("/note/savenote", "/note/update-note/*", "/note/updatenote/*", "/note/update-note/").permitAll()
                .requestMatchers("/note/newnote","/note/newnote/*","/note/delete/*","/note/search","/note/mynote","/note/search/*").permitAll()
                .requestMatchers("/uploadrequest", "/upload/", "/viewrequest", "/requests/*").permitAll()
                .requestMatchers("/uploads/*","/file","fragments/*","/requests","/searchrequest","/requestlist","/searchresult/*").permitAll()
                .requestMatchers("/search/*","/searchpdf/*").permitAll()
                .requestMatchers("/expenses/expense-add","/expenses/expense-view","/expenses/expense-search","/expenses/update/*","/expenses/update/**","expenses/update").permitAll()
                .requestMatchers("/expenses/save","/expenses/recent","/expenses/","/expenses/*","/expenses/delete/*").permitAll()
                .requestMatchers("/expenses/expense-search","/expenses/expense-search/*","/expenses/loadrecent","/expenses/editrecord/*").permitAll()
                .requestMatchers("/f2b/**").permitAll()
                .requestMatchers("/requests/edit/*","/requests/update/*","/requestslist").permitAll()
                .requestMatchers("/payment/payment-add","/payment/save","/recent_payment_perlokal").permitAll()
                .requestMatchers("/share/projectshare-add","/share/projectshare/save","/share/sharelist").permitAll()
                .requestMatchers("/pasugo/subscription-add","/pasugo/subscription/save","/pasugo/pasugolist","/pasugo/search").permitAll()
                .requestMatchers("/wlfr/withdrawal","/wlfr/withdrawal/save","/wlfr/withdrawal/list").permitAll()
                .requestMatchers("/pasugo/encode-mass","/pasugo/save-mass","/pasugo/search/*","/pasugo/gmbalances").permitAll()
                .requestMatchers("/home").authenticated()
                .requestMatchers("/f4/excel-import","/f4/imported-excel","/f4/excel-export","/f4/excel-import-perweek").permitAll()
                .requestMatchers("/f4/search/lokal","/f4/search/district","/f4/search/lokal/*","/f4/search/district/*").permitAll()
                .requestMatchers("/f4/search/wkno","/f4/search/wkno/*","/f4/import/perweek","/f4/import/perweek/*").permitAll()
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
                .headers()
                .frameOptions()
                .sameOrigin()
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
