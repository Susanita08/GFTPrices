package com.gft.es.prices.application.converter;

import com.gft.es.prices.application.model.dto.PriceModel;
import com.gft.es.prices.domain.entity.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;


@ExtendWith(MockitoExtension.class)
class PriceConverterTest {

    private final PriceConverter priceConverter = new PriceConverter();

    @Test
    void testConversionsStartingFromDto() {
        Price u1 = new Price(1L,1L,35455L, Timestamp.valueOf("2020-06-15 16:00:00.0"),
                Timestamp.valueOf("2020-12-31 23:59:59.0"),1, new BigDecimal("38.95"),"EUR");
        PriceModel priceModel = priceConverter.convertFromModel(u1);
        Price u2 = priceConverter.convertFromEntity(priceModel);
        Assertions.assertEquals(u1.getPrice(), u2.getPrice());
    }


    @Test
    void testConversionsStartingFromModel() {
        PriceModel u1 = new PriceModel(1L,1L,35455L, Timestamp.valueOf("2020-06-15 16:00:00.0"),
                Timestamp.valueOf("2020-12-31 23:59:59.0"),1, new BigDecimal("38.95"),"EUR");
        Price price = priceConverter.convertFromEntity(u1);
        PriceModel u2 = priceConverter.convertFromModel(price);
        Assertions.assertEquals(u1.getPrice(), u2.getPrice());
    }

}



