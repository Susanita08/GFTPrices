package com.gft.es.prices.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConstantsUtilsInfra {

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
