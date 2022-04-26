package ru.gb.gbexternalapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
public class GbExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbExternalApiApplication.class, args);
    }

}
