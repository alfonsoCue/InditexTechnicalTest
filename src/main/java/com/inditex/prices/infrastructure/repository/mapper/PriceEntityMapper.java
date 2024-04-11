package com.inditex.prices.infrastructure.repository.mapper;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.repository.model.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    Price toModel(PriceEntity price);
}
