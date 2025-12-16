package com.chaibin.llm.seminar.adapter.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WeatherClientConfig {

    @Bean
    public RestClient weatherRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1")
                .build();
    }

    @Bean
    public RestClient geoRestClient() {
        return RestClient.builder()
                .baseUrl("https://geocoding-api.open-meteo.com/v1")
                .build();
    }
}
