package com.chaibin.llm.seminar.application;

import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.port.output.WeatherPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTests {

    @Mock
    private WeatherPort llmPort;

    @Mock
    private WeatherPort apiPort;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        when(llmPort.searchType()).thenReturn(SearchType.LLM);
        when(apiPort.searchType()).thenReturn(SearchType.API);
        weatherService = new WeatherService(List.of(llmPort, apiPort), true);
    }

    @Test
    void LLM_검색타입이면_LLM_Port를_호출한다() {
        var city = "서울";
        var example = "현재 서울의 기온은 25.0도, 체감 24.0도이며, 풍속은 2m/s입니다. 날씨는 맑음입니다. 옷차림: 반소매 티셔츠와 가벼운 청바지를 추천합니다.";
        when(llmPort.getCurrentWeather(city)).thenReturn(example);

        var result = weatherService.getWeather(city, SearchType.LLM);

        assertThat(result).isEqualTo(example);
        verify(llmPort).getCurrentWeather(city);
        verify(apiPort, never()).getCurrentWeather(anyString());
    }

    @Test
    void API_검색타입이면_API_Port를_호출한다() {
        var city = "부산";
        var example = "현재 부산의 기온은 18.0도, 체감 17.0도이며, 풍속은 5m/s입니다. 날씨는 비입니다. 옷차림: 방수 재킷과 긴바지를 준비하세요.";
        when(apiPort.getCurrentWeather(city)).thenReturn(example);

        var result = weatherService.getWeather(city, SearchType.API);

        assertThat(result).isEqualTo(example);
        verify(apiPort).getCurrentWeather(city);
        verify(llmPort, never()).getCurrentWeather(anyString());
    }

    @Test
    void LLM_비활성화시_API_Port로_대체호출한다() {
        weatherService = new WeatherService(List.of(llmPort, apiPort), false);
        var city = "서울";
        var example = "현재 서울의 기온은 25.0도, 체감 24.0도이며, 풍속은 2m/s입니다. 날씨는 맑음입니다. 옷차림: 반소매 티셔츠와 가벼운 청바지를 추천합니다.";
        when(apiPort.getCurrentWeather(city)).thenReturn(example);

        var result = weatherService.getWeather(city, SearchType.LLM);

        assertThat(result).isEqualTo(example);
        verify(apiPort).getCurrentWeather(city);
        verify(llmPort, never()).getCurrentWeather(anyString());
    }
}
