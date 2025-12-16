package com.chaibin.llm.seminar.domain;

import lombok.Builder;

@Builder
public record Weather(
        String cityName,
        float currentTemperature,
        float feelTemperature,
        String windSpeed,
        int weatherCode
) {

    @Override
    public String toString() {
        return "현재 %s의 기온은 %.1f도, 체감 %.1f도이며, 풍속은 %s입니다. 날씨는 %s입니다. 옷차림은 %s."
                .formatted(cityName, currentTemperature, feelTemperature, windSpeed, mapWeatherCode(weatherCode), clothingSuggestion(weatherCode));
    }

    private String mapWeatherCode(int code) {
        return switch (code) {
            case 0 -> "맑음";
            case 1, 2 -> "대체로 맑음";
            case 3 -> "흐림";
            case 45, 48 -> "안개";
            case 51, 53, 55, 56, 57 -> "이슬비";
            case 61, 63, 65, 66, 67 -> "비";
            case 71, 73, 75, 77 -> "눈";
            case 80, 81, 82 -> "소나기";
            case 85, 86 -> "눈 소나기";
            case 95 -> "뇌우";
            case 96, 99 -> "뇌우(우박)";
            default -> "알 수 없음";
        };
    }

    private String clothingSuggestion(int code) {
        return switch (code) {
            case 0, 1, 2 -> "가벼운 반소매나 얇은 셔츠에 선크림을 챙기세요";
            case 3 -> "얇은 겉옷이나 가디건을 함께 입으세요";
            case 45, 48 -> "시야 확보를 위해 밝은색 겉옷과 가벼운 자켓을 추천합니다";
            case 51, 53, 55, 56, 57 -> "우산을 챙기고 얇은 방수 재킷을 입으세요";
            case 61, 63, 65, 66, 67, 80, 81, 82 -> "우산과 방수 재킷, 미끄럼 방지 신발을 준비하세요";
            case 71, 73, 75, 77, 85, 86 -> "따뜻한 패딩과 방수 부츠, 장갑을 착용하세요";
            case 95, 96, 99 -> "강한 비바람에 대비해 튼튼한 우산이나 방수 재킷을 챙기고 외출을 최소화하세요";
            default -> "현재 계절에 맞는 편안한 겉옷을 선택하세요";
        };
    }
}
