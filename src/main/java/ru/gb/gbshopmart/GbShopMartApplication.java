package ru.gb.gbshopmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gb.gbshopmart.entity.Product;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GbShopMartApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbShopMartApplication.class, args);
    }

}
