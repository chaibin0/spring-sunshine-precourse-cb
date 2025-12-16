package com.chaibin.llm.seminar.adapter.client;

import java.math.BigDecimal;
import java.util.List;

public record GeoResponse(List<GeoResult> results) {}

record GeoResult(
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        String timezone
) {}