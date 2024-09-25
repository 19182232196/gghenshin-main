package com.gghenshinn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class GgghenshinnApplication {
    public static void main(String[] args) {
        SpringApplication.run(GgghenshinnApplication.class, args);
        log.info("server started");
    }
}
