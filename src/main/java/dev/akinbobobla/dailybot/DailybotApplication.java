package dev.akinbobobla.dailybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class DailybotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailybotApplication.class, args);
    }
}