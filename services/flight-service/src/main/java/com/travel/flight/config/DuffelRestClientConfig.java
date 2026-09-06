package com.travel.flight.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class DuffelRestClientConfig {

    @Bean
    public RestClient duffelRestClient(DuffelConfig duffelConfig){

        return RestClient.builder()
                .baseUrl(duffelConfig.getBaseUrl())
                .defaultHeader("Authorization","Bearer "+duffelConfig.getKey())
                .defaultHeader("Duffel-Version",duffelConfig.getVersion())
                .defaultHeader("Accept","application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
