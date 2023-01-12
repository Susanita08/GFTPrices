package com.gft.es.prices.domain.ports.secundary;

import com.gft.es.prices.domain.dto.Price;


import java.time.LocalDateTime;

public interface IPricesServices {
    Price searchPrice(LocalDateTime dateFound, Long productId, Long brandId);
}
