package org.glad2121.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

public class InstantType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public Instant convert(Object o) {
        return toInstant(o);
    }

    static Instant toInstant(Object o) {
        if (o instanceof Temporal) {
            return toInstant((Temporal) o);
        }
        if (o instanceof Long) {
            return toInstant((long) o);
        }
        if (o instanceof Date) {
            return toInstant((Date) o);
        }
        return toInstant(o.toString());
    }

    static Instant toInstant(long millis) {
        return Instant.ofEpochMilli(millis);
    }

    static Instant toInstant(Date date) {
        return date.toInstant();
    }

    static Instant toInstant(Temporal o) {
        if (o instanceof Instant) {
            return (Instant) o;
        }
        if (o instanceof ChronoLocalDateTime) {
            return toInstant((ChronoLocalDateTime<?>) o);
        }
        return Instant.from(o);
    }

    static Instant toInstant(ChronoLocalDateTime<?> dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    static Instant toInstant(CharSequence s) {
        return ZonedDateTime.parse(s).toInstant();
    }

}
