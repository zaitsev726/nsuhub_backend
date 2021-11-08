package ru.nsu.backendmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.backendmodule.config.EmailProperties;

@SpringBootApplication
@EnableConfigurationProperties(EmailProperties.class)
public class BackendModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendModuleApplication.class, args);
    }

}
