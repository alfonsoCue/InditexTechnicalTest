package com.inditex.prices.application.mapper;

import com.inditex.prices.domain.Price;
import com.inditex.prices.infrastructure.model.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponse toResponse(Price price);
}
