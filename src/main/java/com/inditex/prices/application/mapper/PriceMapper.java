package com.inditex.prices.application.mapper;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponse toResponse(Price price);
}
