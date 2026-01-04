package com.example.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient // Enregistrement du service auprès d’Eureka
public class CarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }

    /**
     * Fournit un RestTemplate configuré pour les appels inter-services
     * avec des délais de connexion et de lecture définis
     */
    @Bean
    public RestTemplate createRestTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5_000); // Timeout de connexion (5s)
        factory.setReadTimeout(5_000);    // Timeout de lecture (5s)

        return new RestTemplate(factory);
    }
}
