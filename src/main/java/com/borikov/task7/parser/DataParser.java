package com.borikov.task7.parser;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class DataParser {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char OLD_SYMBOL_REPLACE = '-';
    private static final char NEW_SYMBOL_REPLACE = '_';
    public static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    public static Optional<Date> parseToDate(String data) {
        Optional<Date> dateOptional;
        try {
            dateOptional = Optional.of(simpleDateFormat.parse(data));
        } catch (ParseException e) {
            dateOptional = Optional.empty();
            LOGGER.log(Level.ERROR, "Error while parsing date", e);
        }
        return dateOptional;
    }

    public static String parseToEnumValue(String data) {
        String value = data.replace(OLD_SYMBOL_REPLACE, NEW_SYMBOL_REPLACE);
        return value.toUpperCase();
    }
}
