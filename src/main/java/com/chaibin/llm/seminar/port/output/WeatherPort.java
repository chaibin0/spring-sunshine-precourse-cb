package com.chaibin.llm.seminar.port.output;

import com.chaibin.llm.seminar.core.SearchType;

public interface WeatherPort {

    String getCurrentWeather(String cityName);

    SearchType searchType();
}
