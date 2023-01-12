package com.gft.es.prices.domain.services;

import com.gft.es.prices.domain.dto.Price;
import com.gft.es.prices.domain.exceptions.DateException;
import com.gft.es.prices.domain.ports.secundary.IPricesServices;
import com.gft.es.prices.infrastructure.repository.PricesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.gft.es.prices.utils.ConstantsUtils.FORMAT_INVALIDATE;
import static java.util.Optional.ofNullable;

@Service
public class PricesService implements IPricesServices {
    private static final Log log = LogFactory.getLog(PricesService.class);

    @Autowired
    private final PricesRepository pricesRepository;

    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Override
    public Price searchPrice(LocalDateTime dateFound, Long productId, Long brandId) {
        log.info("Found price to apply in the date: "+dateFound+" ,with of productId: "+productId+" " +
                ", of the brand: "+brandId);
        Timestamp timeDB= Optional.of(Timestamp.valueOf(dateFound)).orElseThrow(
                ()-> new DateException(FORMAT_INVALIDATE));

        return ofNullable(pricesRepository.findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(
                timeDB, productId, brandId)).map(prices -> prices.get(0)).orElse(null);
    }
}
