package org.glad2121.converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

class StringType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public String convert(Object o) {
        return toString(o);
    }

    static String toString(Object o) {
        if (o.getClass() == Date.class) {
            return toString((Date) o);
        }
        return o.toString();
    }

    static String toString(Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toString();
    }

}
