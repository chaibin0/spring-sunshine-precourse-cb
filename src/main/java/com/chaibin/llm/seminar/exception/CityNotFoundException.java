package com.chaibin.llm.seminar.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("해당 도시를 찾을 수 없습니다: " + city);
    }
}
