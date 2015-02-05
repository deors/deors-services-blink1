package deors.services.blink1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Launcher {

    public static void main(String[] args) {

        SpringApplication.run(new Object[] {Launcher.class}, args);
    }
}
