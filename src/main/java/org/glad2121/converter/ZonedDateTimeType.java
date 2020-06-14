package org.glad2121.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

public class ZonedDateTimeType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public ZonedDateTime convert(Object o) {
        return toZonedDateTime(o);
    }

    static ZonedDateTime toZonedDateTime(Object o) {
        if (o instanceof Temporal) {
            return toZonedDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toZonedDateTime((long) o);
        }
        if (o instanceof Date) {
            return toZonedDateTime((Date) o);
        }
        return toZonedDateTime(o.toString());
    }

    static ZonedDateTime toZonedDateTime(long millis) {
        return toZonedDateTime(Instant.ofEpochMilli(millis));
    }

    static ZonedDateTime toZonedDateTime(Date date) {
        return toZonedDateTime(date.toInstant());
    }

    static ZonedDateTime toZonedDateTime(Temporal o) {
        if (o instanceof ZonedDateTime) {
            return (ZonedDateTime) o;
        }
        if (o instanceof Instant) {
            return toZonedDateTime((Instant) o);
        }
        if (o instanceof ChronoLocalDateTime) {
            return toZonedDateTime((ChronoLocalDateTime<?>) o);
        }
        return ZonedDateTime.from(o);
    }

    static ZonedDateTime toZonedDateTime(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    static ZonedDateTime toZonedDateTime(ChronoLocalDateTime<?> dateTime) {
        return ZonedDateTime.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    static ZonedDateTime toZonedDateTime(CharSequence s) {
        return ZonedDateTime.parse(s);
    }

}
