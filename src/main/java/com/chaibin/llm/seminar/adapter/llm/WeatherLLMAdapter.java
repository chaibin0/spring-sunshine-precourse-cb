package com.chaibin.llm.seminar.adapter.llm;

import com.chaibin.llm.seminar.adapter.client.WeatherClientAdapter;
import com.chaibin.llm.seminar.core.SearchType;
import com.chaibin.llm.seminar.port.output.WeatherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
public class WeatherLLMAdapter implements WeatherPort {

    private static final String PROMPT_VERSION = "v1";

    private final ChatClient chatClient;
    private final WeatherClientAdapter weatherClientAdapter;
    private final PromptTemplate promptTemplate;

    public WeatherLLMAdapter(
            ChatClient.Builder builder,
            WeatherClientAdapter weatherClientAdapter
    ) {
        this.chatClient = builder.build();
        this.weatherClientAdapter = weatherClientAdapter;
        this.promptTemplate = loadPromptTemplate();
    }

    public String getCurrentWeather(String city) {

        var prompt = promptTemplate.render(Map.of("city", city));

        var chatResponse = chatClient.prompt(prompt)
                .tools(weatherClientAdapter)
                .call()
                .chatResponse();

        var metadata = chatResponse.getMetadata();
        log.info("model: {}, usage : {}", metadata.getModel(), metadata.getUsage());
        return chatResponse.getResult().getOutput().getText();
    }

    private PromptTemplate loadPromptTemplate() {
        var resource = new ClassPathResource("prompts/weather-llm-" + PROMPT_VERSION + ".txt");
        try {
            var content = resource.getContentAsString(StandardCharsets.UTF_8);
            return new PromptTemplate(content);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load weather LLM prompt (version " + PROMPT_VERSION + ")", e);
        }
    }

    @Override
    public SearchType searchType() {
        return SearchType.LLM;
    }
}
