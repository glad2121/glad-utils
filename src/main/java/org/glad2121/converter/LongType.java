package org.glad2121.converter;

import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Date;

class LongType extends ValueType {

    public LongType(Long defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long convert(Object o) {
        return toLong(o);
    }

    static Long toLong(Object o) {
        if (o instanceof Number) {
            return toLong((Number) o);
        }
        if (o instanceof Boolean) {
            return toLong((boolean) o);
        }
        if (o instanceof Character) {
            return toLong((char) o);
        }
        if (o instanceof Date) {
            return toLong((Date) o);
        }
        if (o instanceof Temporal) {
            return toLong((Temporal) o);
        }
        return toLong(o.toString());
    }

    static long toLong(boolean b) {
        return IntType.toInt(b);
    }

    static long toLong(char c) {
        return IntType.toInt(c);
    }

    static Long toLong(Number n) {
        if (n instanceof Long) {
            return (Long) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer) {
            return n.longValue();
        }
        if (n instanceof BigInteger) {
            return BigInteger.class.cast(n).longValueExact();
        }
        return BigDecimalType.toBigDecimal(n).longValueExact();
    }

    static long toLong(Date date) {
        return date.getTime();
    }

    static long toLong(Temporal o) {
        return InstantType.toInstant(o).toEpochMilli();
    }

    static long toLong(String s) {
        return Long.parseLong(s);
    }

}
