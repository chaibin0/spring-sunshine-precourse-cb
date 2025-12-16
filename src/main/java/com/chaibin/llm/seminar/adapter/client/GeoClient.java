package com.chaibin.llm.seminar.adapter.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GeoClient {

    private final RestClient geoRestClient;

    public GeoResponse searchCity(String name) {
        return geoRestClient.get()
                .uri(uri -> uri
                        .path("/search")
                        .queryParam("name", name)
                        .queryParam("count", 5)
                        .queryParam("language", "ko")
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(GeoResponse.class);
    }
}
