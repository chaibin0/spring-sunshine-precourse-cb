package com.chaibin.llm.seminar.port.input;

import com.chaibin.llm.seminar.core.SearchType;

public interface WeatherUsecase {

    String getWeather(String city, SearchType searchType);
}
