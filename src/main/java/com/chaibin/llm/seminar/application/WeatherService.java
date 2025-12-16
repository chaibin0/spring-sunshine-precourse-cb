package com.chaibin.llm.seminar.application;

import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.port.input.WeatherUsecase;
import com.chaibin.llm.seminar.port.output.WeatherPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService implements WeatherUsecase {

    private final Map<SearchType, WeatherPort> weatherPortMap = new EnumMap<>(SearchType.class);
    private final boolean llmEnabled;

    public WeatherService(List<WeatherPort> weatherPorts,
                          @Value("${weather.llm.enabled:true}") boolean llmEnabled) {
        weatherPorts.forEach(e -> weatherPortMap.put(e.searchType(), e));
        this.llmEnabled = llmEnabled;
    }

    @Override
    public String getWeather(String city, SearchType searchType) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city must not be blank");
        }

        var effectiveType = !llmEnabled ? SearchType.API : searchType;
        var port = weatherPortMap.get(effectiveType);
        if (port == null) {
            throw new IllegalArgumentException("Unsupported search type: " + effectiveType);
        }

        return port.getCurrentWeather(city);
    }
}
