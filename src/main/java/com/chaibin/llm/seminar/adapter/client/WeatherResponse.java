package com.chaibin.llm.seminar.adapter.client;

public record WeatherResponse(Current current) {
}

record Current(
        float temperature_2m,
        float apparent_temperature,
        float wind_speed_10m,
        int weather_code
) {
}
