package com.chaibin.llm.seminar.adapter.web;

import com.chaibin.llm.seminar.exception.CityNotFoundException;
import com.chaibin.llm.seminar.exception.WeatherFetchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCityNotFound(CityNotFoundException ex) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(WeatherFetchException.class)
    public ResponseEntity<ApiErrorResponse> handleWeatherFetch(WeatherFetchException ex) {
        return ResponseEntity.internalServerError().body(new ApiErrorResponse(ex.getMessage()));
    }
}
