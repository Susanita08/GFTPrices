package com.gft.es.prices.application.model.dto;

import com.gft.es.prices.application.validators.ConsistentDateParameters;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class PriceModel {
    private Long priceListId;
    private Long brandId;
    private Long productId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer priority;
    private BigDecimal price;
    private String currencyCode;

    @ConsistentDateParameters
    public PriceModel(Long priceListId, Long brandId, Long productId, Timestamp startDate, Timestamp endDate, Integer priority, BigDecimal price, String currencyCode) {
        this.priceListId = priceListId;
        this.brandId = brandId;
        this.productId = productId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
        this.price = price;
        this.currencyCode = currencyCode;
    }


    @Override
    public String toString() {
        return "Price{" +
                "priceListId=" + priceListId +
                ", brandId=" + brandId +
                ", productId=" + productId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priority=" + priority +
                ", price=" + price +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}