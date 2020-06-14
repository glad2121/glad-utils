package org.glad2121.converter;

import java.math.BigInteger;

class BigIntegerType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public BigInteger convert(Object o) {
        return toBigInteger(o);
    }

    static BigInteger toBigInteger(Object o) {
        if (o instanceof Number) {
            return toBigInteger((Number) o);
        }
        return toBigInteger(o.toString());
    }

    static BigInteger toBigInteger(Number n) {
        if (n instanceof BigInteger) {
            return (BigInteger) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof Long) {
            return BigInteger.valueOf(n.longValue());
        }
        return BigDecimalType.toBigDecimal(n).toBigIntegerExact();
    }

    static BigInteger toBigInteger(String s) {
        return new BigInteger(s);
    }

}
