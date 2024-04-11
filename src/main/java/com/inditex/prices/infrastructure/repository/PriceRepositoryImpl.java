package com.inditex.prices.infrastructure.repository;

import com.inditex.prices.domain.PriceRepository;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.exception.PriceNotFoundException;

import com.inditex.prices.infrastructure.repository.mapper.PriceEntityMapper;
import com.inditex.prices.infrastructure.repository.model.PriceEntity;
import com.inditex.prices.infrastructure.repository.model.PriceEntity_;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;
    private final PriceEntityMapper priceEntityMapper;
    @Override
    public Price searchPriceBy(LocalDateTime date, Long brandId, Long productId) {
        Specification<PriceEntity> spec = (root, query, criteriaBuilder) -> {
            Predicate brandIdPredicate = criteriaBuilder.equal(root.get(PriceEntity_.BRAND_ID), brandId);
            Predicate productIdPredicate = criteriaBuilder.equal(root.get(PriceEntity_.PRODUCT_ID), productId);

            Predicate dateLessOrEqualStartDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get(PriceEntity_.START_DATE), date);
            Predicate dateGreaterOrEqualEndDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get(PriceEntity_.END_DATE), date);

            query.orderBy(criteriaBuilder.desc(root.get(PriceEntity_.PRIORITY)));
            return criteriaBuilder.and(brandIdPredicate, productIdPredicate, dateLessOrEqualStartDatePredicate, dateGreaterOrEqualEndDatePredicate );
        };

        return priceEntityMapper.toModel(
            jpaPriceRepository.findAll(spec).stream()
                .findFirst()
                .orElseThrow(PriceNotFoundException::new)
        );
    }
}
