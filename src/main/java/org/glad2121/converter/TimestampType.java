package org.glad2121.converter;

import java.sql.Timestamp;
import java.time.temporal.Temporal;
import java.util.Date;

class TimestampType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public Timestamp convert(Object o) {
        return toTimestamp(o);
    }

    static Timestamp toTimestamp(Object o) {
        if (o instanceof Date) {
            return toTimestamp((Date) o);
        }
        if (o instanceof Long) {
            return toTimestamp((long) o);
        }
        if (o instanceof Temporal) {
            return toTimestamp((Temporal) o);
        }
        return toTimestamp(o.toString());
    }

    static Timestamp toTimestamp(long millis) {
        return new Timestamp(millis);
    }

    static Timestamp toTimestamp(Date date) {
        if (date instanceof Timestamp) {
            return (Timestamp) date;
        }
        return new Timestamp(date.getTime());
    }

    static Timestamp toTimestamp(Temporal o) {
        return Timestamp.from(InstantType.toInstant(o));
    }

    static Timestamp toTimestamp(String s) {
        return Timestamp.valueOf(s);
    }

}
