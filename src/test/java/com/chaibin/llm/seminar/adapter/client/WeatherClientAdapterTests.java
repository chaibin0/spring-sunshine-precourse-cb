package com.chaibin.llm.seminar.adapter.client;

import com.chaibin.llm.seminar.exception.CityNotFoundException;
import com.chaibin.llm.seminar.exception.WeatherFetchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherClientAdapterTests {

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private GeoClient geoClient;

    @InjectMocks
    private WeatherClientAdapter adapter;

    @Test
    void geoClient가_null을_반환하면_CityNotFoundException을_던진다() {
        when(geoClient.searchCity("서울")).thenReturn(null);

        assertThatThrownBy(() -> adapter.getCurrentWeather("서울"))
                .isInstanceOf(CityNotFoundException.class);
    }

    @Test
    void geoClient가_빈_결과를_반환하면_CityNotFoundException을_던진다() {
        when(geoClient.searchCity("서울")).thenReturn(new GeoResponse(List.of()));

        assertThatThrownBy(() -> adapter.getCurrentWeather("서울"))
                .isInstanceOf(CityNotFoundException.class);
    }

    @Test
    void weatherClient가_null을_반환하면_WeatherFetchException을_던진다() {
        when(geoClient.searchCity("서울")).thenReturn(sampleGeoResponse());
        when(weatherClient.getCurrentForecast(37.0, 127.0, "Asia/Seoul")).thenReturn(null);

        assertThatThrownBy(() -> adapter.getCurrentWeather("서울"))
                .isInstanceOf(WeatherFetchException.class);
    }

    @Test
    void weatherClient가_current_null을_반환하면_WeatherFetchException을_던진다() {
        when(geoClient.searchCity("서울")).thenReturn(sampleGeoResponse());
        when(weatherClient.getCurrentForecast(37.0, 127.0, "Asia/Seoul"))
                .thenReturn(new WeatherResponse(null));

        assertThatThrownBy(() -> adapter.getCurrentWeather("서울"))
                .isInstanceOf(WeatherFetchException.class);
    }

    private GeoResponse sampleGeoResponse() {
        return new GeoResponse(List.of(
                new GeoResult("서울", BigDecimal.valueOf(37.0), BigDecimal.valueOf(127.0), "Asia/Seoul")
        ));
    }
}
