package com.inditex.prices.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Price {
    private final Long id;
    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final Float price;
    private final String currency;
}
