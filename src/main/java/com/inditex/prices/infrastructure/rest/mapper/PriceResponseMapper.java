package com.inditex.prices.infrastructure.rest.mapper;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
    PriceResponse toResponse(Price price);
}
