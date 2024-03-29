package com.inditex.prices.domain;

import java.time.LocalDateTime;
import java.util.List;


public interface PriceRepository {
        Price searchPriceBy(LocalDateTime date, Long brandId, Long productId);
}
