package com.chaibin.llm.seminar.adapter.web;

import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.port.input.WeatherUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherUsecase weatherUsecase;

    @Test
    @DisplayName("도시와 타입을 넘기면 날씨 응답을 반환한다")
    void getWeather_returnsResponse() throws Exception {
        var exampleResponse = "현재 서울의 기온은 25.0도, 체감 24.0도이며, 풍속은 2m/s입니다. 날씨는 맑음입니다. 옷차림: 반소매 티셔츠와 가벼운 청바지를 추천합니다.";
        when(weatherUsecase.getWeather("Seoul", SearchType.LLM)).thenReturn(exampleResponse);

        mockMvc.perform(get("/weather")
                        .param("city", "Seoul")
                        .param("type", "LLM")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(exampleResponse));
    }
}
