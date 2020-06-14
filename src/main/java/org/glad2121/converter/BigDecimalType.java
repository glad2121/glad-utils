package org.glad2121.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

class BigDecimalType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public BigDecimal convert(Object o) {
        return toBigDecimal(o);
    }

    static BigDecimal toBigDecimal(Object o) {
        if (o instanceof Number) {
            return toBigDecimal((Number) o);
        }
        return toBigDecimal(o.toString());
    }

    static BigDecimal toBigDecimal(Number n) {
        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer) {
            return new BigDecimal(n.intValue());
        }
        if (n instanceof Long) {
            return new BigDecimal(n.longValue());
        }
        if (n instanceof Float || n instanceof Double) {
            return new BigDecimal(n.doubleValue());
        }
        if (n instanceof BigInteger) {
            return new BigDecimal((BigInteger) n);
        }
        return new BigDecimal(n.toString());
    }

    static BigDecimal toBigDecimal(String s) {
        return new BigDecimal(s);
    }

}
