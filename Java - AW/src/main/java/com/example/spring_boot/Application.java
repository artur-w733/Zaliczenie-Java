package com.example.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji Spring Boot - Portal Ogłoszeń
 * 
 * @SpringBootApplication - aktywuje:
 *   - @Configuration: konfigurację Spring
 *   - @EnableAutoConfiguration: automatyczną konfigurację
 *   - @ComponentScan: skanowanie komponentów
 * 
 * @author Artur Wróblewski
 */
@SpringBootApplication
public class Application {

    /**
     * Metoda main - punkt startowy aplikacji
     * 
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}