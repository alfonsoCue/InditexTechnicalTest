package com.inditex.prices.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="prices")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "brand_id")
    private final Long brandId;

    @Column(name = "start_date")
    private final LocalDateTime startDate;

    @Column(name = "end_date")
    private final LocalDateTime endDate;

    @Column(name = "price_list")
    private final Integer priceList;

    @Column(name = "product_id")
    private final Long productId;

    @Column(name = "priority")
    private final Integer priority;

    @Column(name = "price")
    private final Float price;

    @Column(name = "curr")
    private final String curr;

}

