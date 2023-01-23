package com.gft.es.prices.application.service;

import com.gft.es.prices.application.converter.PriceConverter;
import com.gft.es.prices.application.model.dto.PriceModel;
import com.gft.es.prices.domain.entity.Price;
import com.gft.es.prices.domain.exceptions.DateException;
import com.gft.es.prices.infrastructure.ports.secundary.IAdapterH2Services;
import com.gft.es.prices.application.repository.PricesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static com.gft.es.prices.application.utils.ConstantsUtilsApp.FORMAT_INVALIDATE;

@Service
public class PriceBDServices implements IAdapterH2Services {
    private static final Log log = LogFactory.getLog(PriceBDServices.class);

    @Autowired
    private final PricesRepository pricesRepository;

    @Autowired
    private final PriceConverter priceConverter;

    public PriceBDServices(PricesRepository pricesRepository,
                           PriceConverter priceConverter) {
        this.pricesRepository = pricesRepository;
        this.priceConverter = priceConverter;
    }

    @Override
    public PriceModel searchPrice(LocalDateTime dateFound, Long productId, Long brandId) {
        log.info("Found price to apply in the date: "+dateFound+" " +
                ",with of productId: "+productId+" " +
                ", of the brand: "+brandId);

        Timestamp timeDB= Optional.of(Timestamp.valueOf(dateFound))
                .orElseThrow(()-> new DateException(FORMAT_INVALIDATE));

        Price price= ofNullable(pricesRepository
                .findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(
                timeDB, productId, brandId))
                .map(prices -> prices.get(0)).orElse(null);

        return priceConverter.convertFromModel(price);
    }
}
