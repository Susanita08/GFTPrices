package com.gft.es.prices.application.service;

import com.gft.es.prices.application.converter.PriceConverter;
import com.gft.es.prices.application.model.PriceModel;
import com.gft.es.prices.application.model.responses.JsonOutputPrices;
import com.gft.es.prices.application.enums.ApplicationMessage;
import com.gft.es.prices.infrastructure.ports.primary.IPriceService;
import com.gft.es.prices.infrastructure.ports.secundary.IAdapterH2Services;
import com.gft.es.prices.infrastructure.config.SelfConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.gft.es.prices.application.utils.ConstantsUtilsApp.FORMAT_INVALIDATE;
import static com.gft.es.prices.application.utils.ConstantsUtilsApp.FAILED_QUERY;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
public class PriceService implements IPriceService {
    @Autowired
    private final SelfConfiguration selfConfiguration;

    @Autowired
    private final PriceConverter priceConverter;

    @Autowired
    private final IAdapterH2Services adapterH2Services;

    public PriceService(SelfConfiguration selfConfiguration, IAdapterH2Services adapterH2Services,
                        PriceConverter priceConverter) {
        this.selfConfiguration = selfConfiguration;
        this.adapterH2Services =adapterH2Services;
        this.priceConverter = priceConverter;
    }

    @Override
    public JsonOutputPrices adapterOutputPrices(LocalDateTime dateFound, Long productId, Long brandId) {
        if (isNull(dateFound) || isNull(productId) || isNull(brandId)) {
            return getBadResponse();
        }
        PriceModel price = adapterH2Services.searchPrice(dateFound,productId,brandId);

        return ofNullable(price).map(p -> setResponseOutputPrice(p,dateFound))
                .orElse(JsonOutputPrices.builder().response(
                        setResponsePrice(ApplicationMessage.UNEXPECTED, FAILED_QUERY)).build());
    }

    private JsonOutputPrices setResponseOutputPrice(PriceModel price, LocalDateTime timeDB){
        double tax=selfConfiguration.getTax();
        JsonOutputPrices.Response response = setResponsePrice(ApplicationMessage.SUCCESS, "");
        JsonOutputPrices jsonOutputPrices = new JsonOutputPrices();
        jsonOutputPrices.setProductId(price.getProductId());
        jsonOutputPrices.setBrandId(price.getBrandId());
        jsonOutputPrices.setDateToFound(timeDB);
        jsonOutputPrices.setPrice(priceConverter.convertFromEntity(price));
        jsonOutputPrices.setRateToApply(price.getPrice());
        jsonOutputPrices.setTax(tax);
        jsonOutputPrices.setFinalPrice(calculateFinalPrice(price.getPrice()));
        jsonOutputPrices.setResponse(response);
        return jsonOutputPrices;
    }

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal price) {
        return price.add(price.multiply(BigDecimal.valueOf(selfConfiguration.getTax())))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public JsonOutputPrices.Response setResponsePrice(ApplicationMessage application, String description) {
        return JsonOutputPrices.Response.builder().
                code(application.getCode())
                .message(application.getMessage())
                .strCode(application.getStrCode())
                .description(description).build();
    }

    public JsonOutputPrices getBadResponse() {
        return JsonOutputPrices.builder().response(setResponsePrice(ApplicationMessage.UNEXPECTED,
                FORMAT_INVALIDATE)).build();
    }
}
