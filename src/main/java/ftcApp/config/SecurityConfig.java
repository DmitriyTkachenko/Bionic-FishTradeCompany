package ftcApp.config;

import ftcApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // register UserDetailsService implementation and PasswordEncoder for BCrypt
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/dashboard/").hasAnyAuthority("GENERAL_MANAGER", "COLD_STORE_MANAGER", "ACCOUNTANT", "SECURITY_OFFICER")
                .antMatchers("/dashboard/**").hasAnyAuthority("GENERAL_MANAGER", "COLD_STORE_MANAGER", "ACCOUNTANT", "SECURITY_OFFICER")
                .antMatchers("/dashboard").hasAnyAuthority("GENERAL_MANAGER", "COLD_STORE_MANAGER", "ACCOUNTANT", "SECURITY_OFFICER")
                .antMatchers("/profile/").hasAnyAuthority("CUSTOMER")
                .antMatchers("/profile/**").hasAnyAuthority("CUSTOMER")
                .antMatchers("/profile").hasAnyAuthority("CUSTOMER");

        // protection from CSRF attacks
        http.csrf()
                .disable()
                // rules for requests that define access for resources
                .authorizeRequests()
                .antMatchers("/resources/**", "/**").permitAll()
                .anyRequest().permitAll()
                .and();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/log-in-error/")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                        // allow everyone to have access to login form
                .permitAll()
                .successHandler(getAuthenticationSuccessHandler());

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        RoleBasedAuthenticationSuccessHandler roleBasedAuthenticationSuccessHandler = new RoleBasedAuthenticationSuccessHandler();
        HashMap<String, String> roleUrlMap = new HashMap<>();
        roleUrlMap.put("CUSTOMER", "/shop/");
        roleUrlMap.put("GENERAL_MANAGER", "/dashboard/");
        roleUrlMap.put("COLD_STORE_MANAGER", "/dashboard/");
        roleUrlMap.put("ACCOUNTANT", "/dashboard/");
        roleUrlMap.put("SECURITY_OFFICER", "/dashboard/");
        roleBasedAuthenticationSuccessHandler.setRoleUrlMap(roleUrlMap);
        return roleBasedAuthenticationSuccessHandler;
    }
}