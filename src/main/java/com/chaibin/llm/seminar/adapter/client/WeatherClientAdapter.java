package com.chaibin.llm.seminar.adapter.client;


import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.domain.Weather;
import com.chaibin.llm.seminar.exception.CityNotFoundException;
import com.chaibin.llm.seminar.exception.WeatherFetchException;
import com.chaibin.llm.seminar.port.output.WeatherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherClientAdapter implements WeatherPort {

    private final WeatherClient weatherClient;
    private final GeoClient geoClient;

    @Tool(description = "Get the current weather for the given cityName (e.g., \"Seoul\" or \"New York\").")
    public String getCurrentWeather(String cityName) {
        var geo = geoClient.searchCity(cityName);

        if (geo == null || geo.results() == null || geo.results().isEmpty()) {
            throw new CityNotFoundException(cityName);
        }

        var first = geo.results().getFirst();

        var weather = weatherClient.getCurrentForecast(
                first.latitude().doubleValue(),
                first.longitude().doubleValue(),
                first.timezone()
        );

        if (weather == null || weather.current() == null) {
            throw new WeatherFetchException("날씨 정보를 가져오지 못했습니다: " + cityName);
        }

        var current = weather.current();

        return Weather.builder()
                .cityName(cityName)
                .currentTemperature(current.temperature_2m())
                .feelTemperature(current.apparent_temperature())
                .windSpeed(current.wind_speed_10m() + " m/s")
                .weatherCode(current.weather_code())
                .build()
                .toString();
    }

    @Override
    public SearchType searchType() {
        return SearchType.API;
    }

}
