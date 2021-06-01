package hr.obai.tacoapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
    scanBasePackages = {
        "hr.obai.tacoapplication",
        "hr.obai.taco.domain"
    }
)
public class TacoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoApplication.class, args);
    }
}
