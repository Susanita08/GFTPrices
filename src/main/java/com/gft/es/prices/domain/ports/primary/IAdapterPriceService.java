package com.gft.es.prices.domain.ports.primary;

import com.gft.es.prices.api.response.JsonOutputPrices;
import com.gft.es.prices.domain.enums.ApplicationMessage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IAdapterPriceService {
    JsonOutputPrices adapterOutputPrices(LocalDateTime dateFound, Long productId, Long brandId);
    BigDecimal calculateFinalPrice(BigDecimal price);
    JsonOutputPrices.Response setResponsePrice(ApplicationMessage application, String description);
    LocalDateTime parseDate(String dateFound);
}
