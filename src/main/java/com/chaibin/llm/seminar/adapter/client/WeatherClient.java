package com.chaibin.llm.seminar.adapter.client;

import com.chaibin.llm.seminar.exception.WeatherFetchException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestClient weatherRestClient;

    public WeatherResponse getCurrentForecast(double latitude, double longitude, String timezone) {
        try {
            var response = weatherRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m,apparent_temperature,wind_speed_10m,weather_code")
                            .queryParam("timezone", timezone)
                            .build())
                    .retrieve()
                    .body(WeatherResponse.class);

            if (response == null || response.current() == null) {
                throw new WeatherFetchException("날씨 정보를 가져오지 못했습니다.");
            }
            return response;
        } catch (RestClientException ex) {
            throw new WeatherFetchException("날씨 API 호출 중 오류가 발생했습니다.", ex);
        }
    }
}
