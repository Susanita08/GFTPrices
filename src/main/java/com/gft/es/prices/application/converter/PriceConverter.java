package com.gft.es.prices.application.converter;

import com.gft.es.prices.application.model.PriceModel;
import com.gft.es.prices.domain.entity.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceConverter extends Converter<Price, PriceModel> {
    public PriceConverter() {
        super(PriceConverter::convertToEntity, PriceConverter::convertToModel);
    }

    private static Price convertToModel(PriceModel priceModel) {
        return new Price(priceModel.getPriceListId(),
                priceModel.getBrandId(),
                priceModel.getProductId(),
                priceModel.getStartDate(),
                priceModel.getEndDate(),
                priceModel.getPriority(),
                priceModel.getPrice(),
                priceModel.getCurrencyCode());

    }

    private static PriceModel convertToEntity(Price dto) {
        return new PriceModel(dto.getPriceListId(),
                dto.getBrandId(),
                dto.getProductId(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getPriority(),
                dto.getPrice(),
                dto.getCurrencyCode());
    }

}
