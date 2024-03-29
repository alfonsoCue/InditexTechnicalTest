package com.inditex.prices.infraestructure.repository;

import com.inditex.prices.domain.Price;
import com.inditex.prices.domain.PriceRepository;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;
    @Override
    public Price searchPriceBy(LocalDateTime date, Long brandId, Long productId) {
        Specification<Price> spec = (root, query, criteriaBuilder) -> {
            Predicate brandIdPredicate = criteriaBuilder.equal(root.get("brandId"), brandId);
            Predicate productIdPredicate = criteriaBuilder.equal(root.get("productId"), productId);

            Predicate dateLessOrEqualStartDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), date);
            Predicate dateGreaterOrEqualEndDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), date);

            query.orderBy(criteriaBuilder.desc(root.get("priority")));
            return criteriaBuilder.and(brandIdPredicate, productIdPredicate, dateLessOrEqualStartDatePredicate, dateGreaterOrEqualEndDatePredicate );
        };

        return jpaPriceRepository.findAll(spec).stream()
                .findFirst()
                .orElseThrow(() -> new PriceNotFoundException(String.format("Not prices found for date: %s, brandId: %s, productId: %s", date, brandId, productId)));
    }
}
