package com.gft.es.prices.infraestructura.service;

import com.gft.es.prices.application.converter.PriceConverter;
import com.gft.es.prices.application.model.dto.PriceModel;
import com.gft.es.prices.application.model.responses.JsonOutputPrices;
import com.gft.es.prices.application.model.responses.JsonOutputPricesMother;
import com.gft.es.prices.application.repository.PricesRepository;
import com.gft.es.prices.application.service.PriceBDServices;
import com.gft.es.prices.application.utils.LocalDateFormatter;

import com.gft.es.prices.domain.entity.Price;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PricesDBServiceImplTest {
    @Mock
    private PricesRepository pricesRepository;

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(1L, 35455L, "2020-06-14-10.00.00", "2020-06-14 00:00:00.0", "2020-12-31 23:59:59.0",
                        new BigDecimal("35.5")),
                Arguments.of(1L, 35455L, "2020-06-14-16.00.00", "2020-06-14 15:00:00.0", "2020-06-14 18:30:00.0",
                        new BigDecimal("25.45")),
                Arguments.of(1L, 35455L, "2020-06-14-21.00.00", "2020-06-14 00:00:00.0", "2020-12-31 23:59:59.0",
                        new BigDecimal("35.5")),
                Arguments.of(1L, 35455L, "2020-06-15-10.00.00", "2020-06-15 00:00:00.0", "2020-06-15 11:00:00.0",
                        new BigDecimal("30.5")),
                Arguments.of(1L, 35455L, "2020-06-16-21.00.00", "2020-06-15 16:00:00.0", "2020-12-31 23:59:59.0",
                        new BigDecimal("38.95"))
        );
    }

    public static Stream<Arguments> testCasesWithError() {
        return Stream.of(
                Arguments.of(1L, null, "2020-06-14-10.00.00"), Arguments.of(null, 35455L, "2020-06-14-21.00.00"),
                Arguments.of(0L, 35455L, "2020-06-16-21.00.00")
        );
    }

    public static Stream<Arguments> testCasesWithDateNull() {
        return Stream.of(
                Arguments.of(1L, 35455L, null), Arguments.of(null, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void givenPriceWithoutErrors(Long brandId, Long productId, String dateString, String startDate, String endDate,
                                 BigDecimal price) throws Exception {
        pricesRepository = mock(PricesRepository.class);
        PriceConverter priceConverter = new PriceConverter();

        JsonOutputPricesMother jsonOutputPricesMother = new JsonOutputPricesMother();
        JsonOutputPrices jsonOutputPricesExpected = jsonOutputPricesMother.getJsonOutputPrices(productId, brandId,
                dateString, startDate, endDate, price);
        LocalDateTime localDateTime = jsonOutputPricesExpected.getDateToFound();

        when(pricesRepository.findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(any(), any(), any()))
                .thenReturn(List.of(jsonOutputPricesExpected.getPrice()));

        PriceBDServices pricesDBService = new PriceBDServices(pricesRepository, priceConverter);

        final PriceModel result = pricesDBService.searchPrice(localDateTime, productId, brandId);

        verify(pricesRepository).findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(any(), any(), any());

        assertEquals(priceConverter.convertFromModel(jsonOutputPricesExpected.getPrice()).toString(), result.toString());
    }

    @ParameterizedTest
    @MethodSource("testCasesWithError")
    void givenPriceWithErrors(Long brandId, Long productId, String dateString) {
        pricesRepository = mock(PricesRepository.class);
        PriceConverter priceConverter = new PriceConverter();

        LocalDateTime dateTime = null;
        PriceBDServices pricesService = new PriceBDServices(pricesRepository, priceConverter);

        when(pricesRepository.findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(any(), any(), any()))
                .thenReturn(null);

        if (nonNull(dateString)) {
            dateTime = LocalDateFormatter.getDateToFind(dateString);
        } else {
            assertThrows(RuntimeException.class, () -> pricesService.searchPrice(null, productId, brandId));
        }

        LocalDateTime finalDate = dateTime;
        final PriceModel result = pricesService.searchPrice(finalDate, productId, brandId);

        assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("testCasesWithDateNull")
    void givenPriceWithErrorsForDateNull(Long brandId, Long productId, String dateString) {
        pricesRepository = mock(PricesRepository.class);
        PriceConverter priceConverter = new PriceConverter();

        LocalDateTime dateTime = null;
        PriceBDServices pricesService = new PriceBDServices(pricesRepository, priceConverter);


        if (nonNull(dateString)) {
            dateTime = LocalDateFormatter.getDateToFind(dateString);
        }

        LocalDateTime finalDateTime = dateTime;
        assertThrows(RuntimeException.class, () -> pricesService.searchPrice(finalDateTime, productId, brandId));
    }

    private PriceModel getPrice(Price price){
        return PriceModel.builder()
                .priceListId(price.getPriceListId())
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priority(price.getPriority())
                .price(price.getPrice())
                .currencyCode(price.getCurrencyCode()).build();
    }
}