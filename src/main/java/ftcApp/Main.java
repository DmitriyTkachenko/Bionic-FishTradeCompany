package ftcApp;


import ftcApp.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class Main {
    @Inject
    UserService userService;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
        new Main().userService.getHashedPassword("dasfas");
        System.out.println("Done.");
    }
}
