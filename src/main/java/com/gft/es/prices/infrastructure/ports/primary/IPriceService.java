package com.gft.es.prices.infrastructure.ports.primary;

import com.gft.es.prices.application.model.responses.JsonOutputPrices;
import com.gft.es.prices.application.enums.ApplicationMessage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IPriceService {
    JsonOutputPrices adapterOutputPrices(LocalDateTime dateFound, Long productId, Long brandId);
    BigDecimal calculateFinalPrice(BigDecimal price);
    JsonOutputPrices.Response setResponsePrice(ApplicationMessage application, String description);
}
