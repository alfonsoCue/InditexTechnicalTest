package com.inditex.prices.domain;

import com.inditex.prices.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceRepository {
        Price searchPriceBy(LocalDateTime date, Long brandId, Long productId);
}
