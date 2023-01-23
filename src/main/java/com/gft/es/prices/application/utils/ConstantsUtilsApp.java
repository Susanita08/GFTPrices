package com.gft.es.prices.application.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ConstantsUtilsApp {

    /*Message for declaration's exceptions*/
    public static final String LIST_PRICES_EMPTY="List price is empty";
    public static final String FAILED_QUERY="Failed price query.";
    public static final String FORMAT_INVALIDATE = "Invalidate parameters.";    /*Controller's paths*/
}
