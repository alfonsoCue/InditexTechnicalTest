package com.inditex.prices.infrastructure.rest;

import com.inditex.prices.application.PriceService;
import com.inditex.prices.infrastructure.rest.api.PriceApi;
import com.inditex.prices.infrastructure.rest.mapper.PriceResponseMapper;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final PriceService searchService;
    private final PriceResponseMapper priceResponseMapper;

    @Override
    public ResponseEntity<PriceResponse> searchPrice(LocalDateTime date, Long productId, Long brandId) {

        return ResponseEntity.ok(
                priceResponseMapper.toResponse(
                    searchService.search(date, brandId, productId))
                );
    }
}
