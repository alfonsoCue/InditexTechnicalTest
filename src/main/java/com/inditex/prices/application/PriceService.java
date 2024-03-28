package com.inditex.prices.application;

import com.inditex.prices.application.mapper.PriceMapper;
import com.inditex.prices.domain.exception.PriceNotFoundException;

import com.inditex.prices.domain.Price;
import com.inditex.prices.domain.PriceRepository;
import com.inditex.prices.infrastructure.model.PriceResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceResponse search(LocalDateTime date, Long brandId, Long productId)
    {
       Specification<Price> spec = (root, query, criteriaBuilder) -> {
            Predicate brandIdPredicate = criteriaBuilder.equal(root.get("brandId"), brandId);
            Predicate productIdPredicate = criteriaBuilder.equal(root.get("productId"), productId);

            Predicate dateLessOrEqualStartDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), date);
            Predicate dateGreaterOrEqualEndDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), date);

            query.orderBy(criteriaBuilder.desc(root.get("priority")));
            return criteriaBuilder.and(brandIdPredicate, productIdPredicate, dateLessOrEqualStartDatePredicate, dateGreaterOrEqualEndDatePredicate );
        };



        //Specification<Price> searchByDateBrandIdProductId = searchByDateBrandIdProductId(localDateTime, brandId, productId);
        Price price = this.priceRepository.findAll(spec)
                .stream().findFirst()
                .orElseThrow(()-> new PriceNotFoundException("Price not found"));
        return priceMapper.toResponse(price);
    }

/*    private Specification<Price> searchByDateBrandIdProductId(LocalDateTime date, Long brandId, Long productId)
    {

    }*/
}
