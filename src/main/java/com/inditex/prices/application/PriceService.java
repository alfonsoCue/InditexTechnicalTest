package com.inditex.prices.application;

import com.inditex.prices.application.mapper.PriceMapper;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.PriceRepository;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceResponse search(LocalDateTime date, Long brandId, Long productId)
    {
        Price price = this.priceRepository.searchPriceBy(date, brandId, productId);
        return priceMapper.toResponse(price);
    }
}
