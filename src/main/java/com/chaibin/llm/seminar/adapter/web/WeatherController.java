package com.chaibin.llm.seminar.adapter.web;

import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.port.input.WeatherUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherUsecase weatherUsecase;

    @GetMapping("/weather")
    public ResponseEntity<GetWeatherResponseDto> getWeather(
            @RequestParam("city") String city,
            @RequestParam(value = "type", defaultValue = "LLM") SearchType searchType
    ) {
        var result = weatherUsecase.getWeather(city, searchType);
        return ResponseEntity.ok(new GetWeatherResponseDto(result));
    }
}
