package org.glad2121.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;

class LocalDateType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public LocalDate convert(Object o) {
        return toLocalDate(o);
    }

    static LocalDate toLocalDate(Object o) {
        if (o instanceof Temporal) {
            return toLocalDate((Temporal) o);
        }
        if (o instanceof Long) {
            return toLocalDate((long) o);
        }
        if (o instanceof Date) {
            return toLocalDate((Date) o);
        }
        return toLocalDate(o.toString());
    }

    static LocalDate toLocalDate(long millis) {
        return toLocalDate(Instant.ofEpochMilli(millis));
    }

    static LocalDate toLocalDate(Date date) {
        return toLocalDate(date.toInstant());
    }

    static LocalDate toLocalDate(Temporal o) {
        if (o instanceof LocalDate) {
            return (LocalDate) o;
        }
        if (o instanceof Instant) {
            return toLocalDate((Instant) o);
        }
        return LocalDate.from(o);
    }

    static LocalDate toLocalDate(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }

    static LocalDate toLocalDate(CharSequence s) {
        return LocalDate.parse(s);
    }

}
