package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private String welcome_message;
    
    public WelcomeController(@Value("${welcome.message}") String a_welcome_message) {
        this.welcome_message = a_welcome_message;
    }

    @GetMapping("/")
    public String sayHello() {
        return welcome_message;
    }
}
