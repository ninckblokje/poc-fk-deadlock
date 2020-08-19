package ninckblokje.poc.db.deadlock.fk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PocFkDeadlockApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocFkDeadlockApplication.class, args);
    }

}
