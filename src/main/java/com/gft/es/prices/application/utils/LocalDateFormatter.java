package com.gft.es.prices.application.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return DateTimeFormatter.ISO_DATE.format(object);
    }

    public static LocalDateTime getDateToFind(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(date, formatter);
    }
}