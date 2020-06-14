package org.glad2121.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;

class LocalDateTimeType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public LocalDateTime convert(Object o) {
        return toLocalDateTime(o);
    }

    static LocalDateTime toLocalDateTime(Object o) {
        if (o instanceof Temporal) {
            return toLocalDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toLocalDateTime((long) o);
        }
        if (o instanceof Date) {
            return toLocalDateTime((Date) o);
        }
        return toLocalDateTime(o.toString());
    }

    static LocalDateTime toLocalDateTime(long millis) {
        return toLocalDateTime(Instant.ofEpochMilli(millis));
    }

    static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date.toInstant());
    }

    static LocalDateTime toLocalDateTime(Temporal o) {
        if (o instanceof LocalDateTime) {
            return (LocalDateTime) o;
        }
        if (o instanceof Instant) {
            return toLocalDateTime((Instant) o);
        }
        return LocalDateTime.from(o);
    }

    static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    static LocalDateTime toLocalDateTime(CharSequence s) {
        return LocalDateTime.parse(s);
    }

}
