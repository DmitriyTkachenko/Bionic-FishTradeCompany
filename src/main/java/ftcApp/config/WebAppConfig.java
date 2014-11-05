package ftcApp.config;

import ftcApp.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("ftcApp")
@Import({ SecurityConfig.class })
@ImportResource({"file:**/resources/META-INF/app-context.xml"})
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

}
