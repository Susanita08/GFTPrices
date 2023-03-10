package com.gft.es.prices.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Validated
@Builder
@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Price {

    @Column(name = "price_list_id", nullable = false)
    @GeneratedValue(generator = "Sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "Sequence", sequenceName = "SEQUENCE_PRICE", allocationSize = 1)
    @Id
    private Long priceListId;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "product_id")
    private Long productId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH.mm.ss")
    @Column(name = "start_date")
    private Timestamp startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH.mm.ss")
    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "priority")
    private Integer priority;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 9, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "currency")
    private String currencyCode;

}

