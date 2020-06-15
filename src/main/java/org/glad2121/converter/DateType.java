package org.glad2121.converter;

import java.time.temporal.Temporal;
import java.util.Date;

class DateType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public Date convert(Object o) {
        return toDate(o);
    }

    static Date toDate(Object o) {
        if (o instanceof Date) {
            return (Date) o;
        }
        if (o instanceof Long) {
            return toDate((long) o);
        }
        if (o instanceof Temporal) {
            return toDate((Temporal) o);
        }
        return toDate(o.toString());
    }

    static Date toDate(long millis) {
        return new Date(millis);
    }

    static Date toDate(Temporal temporal) {
        return Date.from(InstantType.toInstant(temporal));
    }

    static Date toDate(String s) {
        return Date.from(InstantType.toInstant(s));
    }

}
