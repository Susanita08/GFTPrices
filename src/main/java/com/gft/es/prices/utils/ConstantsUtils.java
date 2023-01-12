package com.gft.es.prices.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConstantsUtils{

    /*Message for declaration's exceptions*/
    public static final String END_DATE_LESS_THAT_START_DATE_ERROR="The end date cannot be less than the start date.";
    public static final String FORMAT_INVALIDATE="Invalidate parameters.";
    public static final String LIST_PRICES_EMPTY="List price is empty";
    public static final String ILEGAL_PARAMETER_DATE_ERROR="Illegal method signature.";
    public static final String FAILED_QUERY="Failed price query.";

    /*Controller's paths*/
    public static final String PATH_SEPARATOR="/";
    public static final String HEALTH="health";
    public static final String PRICES="prices";
    public static final String API_VERSION="api/v1";
    public static final String FIND_PRICE="findPrice";
    public static final String PRODUCT_ID="{productId}";
    public static final String BRAND_ID="{brandId}";
    public static final String PRICE_LIST_ID="{id}";
}
