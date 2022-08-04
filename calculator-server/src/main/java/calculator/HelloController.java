package calculator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Hello World! " + LocalDateTime.now();
    }
}
