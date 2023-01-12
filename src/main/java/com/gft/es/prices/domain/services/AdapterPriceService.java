package com.gft.es.prices.domain.services;

import com.gft.es.prices.api.response.JsonOutputPrices;
import com.gft.es.prices.domain.dto.Price;
import com.gft.es.prices.domain.enums.ApplicationMessage;
import com.gft.es.prices.domain.ports.primary.IAdapterPriceService;
import com.gft.es.prices.domain.ports.secundary.IPricesServices;
import com.gft.es.prices.infrastructure.config.SelfConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static com.gft.es.prices.utils.ConstantsUtils.FAILED_QUERY;
import static com.gft.es.prices.utils.ConstantsUtils.FORMAT_INVALIDATE;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Service
public class AdapterPriceService implements IAdapterPriceService {

    @Autowired
    private final SelfConfiguration selfConfiguration;

    @Autowired
    private final IPricesServices pricesServices;

    public AdapterPriceService(SelfConfiguration selfConfiguration, IPricesServices pricesServices) {
        this.selfConfiguration = selfConfiguration;
        this.pricesServices=pricesServices;
    }

    @Override
    public JsonOutputPrices adapterOutputPrices(LocalDateTime dateFound, Long productId, Long brandId) {
        if (isNull(dateFound) || isNull(productId) || isNull(brandId)) {
            return getBadResponse();
        }
        Price price = pricesServices.searchPrice(dateFound,productId,brandId);

        return ofNullable(price).map(p -> setResponseOutputPrice(p,dateFound))
                .orElse(JsonOutputPrices.builder().response(
                        setResponsePrice(ApplicationMessage.UNEXPECTED, FAILED_QUERY)).build());
    }

    private JsonOutputPrices setResponseOutputPrice(Price price, LocalDateTime timeDB){
        double tax=selfConfiguration.getTax();
        JsonOutputPrices.Response response = setResponsePrice(ApplicationMessage.SUCCESS, "");
        JsonOutputPrices jsonOutputPrices = new JsonOutputPrices();
        jsonOutputPrices.setProductId(price.getProductId());
        jsonOutputPrices.setBrandId(price.getBrandId());
        jsonOutputPrices.setDateToFound(timeDB);
        jsonOutputPrices.setPrice(price);
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

    @Override
    public LocalDateTime parseDate(String dateFound) {
        try{
            DateTimeFormatter originalFormat = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive().append(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")).toFormatter();

            return LocalDateTime.parse(dateFound, originalFormat);
        }catch(Exception e){
            DateTimeFormatter commonFormat = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive().append(
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toFormatter();
            return LocalDateTime.parse(dateFound, commonFormat);
        }
    }

    public JsonOutputPrices getBadResponse() {
        return JsonOutputPrices.builder().response(setResponsePrice(ApplicationMessage.UNEXPECTED,
                FORMAT_INVALIDATE)).build();
    }
}
