package com.gft.es.prices.infrastructure.ports.secundary;

import com.gft.es.prices.application.model.dto.PriceModel;


import java.time.LocalDateTime;

public interface IAdapterH2Services {
    PriceModel searchPrice(LocalDateTime dateFound, Long productId, Long brandId);
}
