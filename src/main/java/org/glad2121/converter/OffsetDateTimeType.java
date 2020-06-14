package org.glad2121.converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

class OffsetDateTimeType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public OffsetDateTime convert(Object o) {
        return toOffsetDateTime(o);
    }

    static OffsetDateTime toOffsetDateTime(Object o) {
        if (o instanceof Temporal) {
            return toOffsetDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toOffsetDateTime((long) o);
        }
        if (o instanceof Date) {
            return toOffsetDateTime((Date) o);
        }
        return toOffsetDateTime(o.toString());
    }

    static OffsetDateTime toOffsetDateTime(long millis) {
        return toOffsetDateTime(Instant.ofEpochMilli(millis));
    }

    static OffsetDateTime toOffsetDateTime(Date date) {
        return toOffsetDateTime(date.toInstant());
    }

    static OffsetDateTime toOffsetDateTime(Temporal o) {
        if (o instanceof OffsetDateTime) {
            return (OffsetDateTime) o;
        }
        if (o instanceof Instant) {
            return toOffsetDateTime((Instant) o);
        }
        if (o instanceof ChronoLocalDateTime) {
            return toOffsetDateTime((ChronoLocalDateTime<?>) o);
        }
        return OffsetDateTime.from(o);
    }

    static OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    static OffsetDateTime toOffsetDateTime(ChronoLocalDateTime<?> dateTime) {
        return OffsetDateTime.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    static OffsetDateTime toOffsetDateTime(CharSequence s) {
        return OffsetDateTime.parse(s);
    }

}
