package com.inditex.prices.infraestructure.repository;

import com.inditex.prices.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceRepository extends JpaRepository<Price, Long>, JpaSpecificationExecutor<Price> {
}
