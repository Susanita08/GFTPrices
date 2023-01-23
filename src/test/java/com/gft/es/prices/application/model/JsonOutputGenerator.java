package com.gft.es.prices.application.model;

import com.gft.es.prices.application.model.responses.JsonOutputPrices;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;


@Profile("test")
public interface JsonOutputGenerator {
    JsonOutputPrices getJsonOutputPrices(Long productId, Long brandId, String dateString, String startDate, String endDate, BigDecimal price);
    JsonOutputPrices getJsonOutputPricesConstants(Long productId, Long brandId);

}
