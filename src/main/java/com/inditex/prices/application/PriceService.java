package com.inditex.prices.application;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.PriceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public Price search(LocalDateTime date, Long brandId, Long productId)
    {
        return this.priceRepository.searchPriceBy(date, brandId, productId);
    }
}
