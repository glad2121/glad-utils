package org.glad2121.converter;

import java.math.BigInteger;

class IntType extends ValueType {

    IntType(Integer defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer convert(Object o) {
        return toInteger(o);
    }

    static Integer toInteger(Object o) {
        if (o instanceof Number) {
            return toInteger((Number) o);
        }
        if (o instanceof Boolean) {
            return toInt((boolean) o);
        }
        if (o instanceof Character) {
            return toInt((char) o);
        }
        return toInt(o.toString());
    }

    static int toInt(boolean b) {
        return b ? 1 : 0;
    }

    static int toInt(char c) {
        return c & 0xFFFF;
    }

    static Integer toInteger(Number n) {
        if (n instanceof Integer) {
            return (Integer) n;
        }
        if (n instanceof Byte || n instanceof Short) {
            return n.intValue();
        }
        if (n instanceof Long || n instanceof BigInteger) {
            return BigIntegerType.toBigInteger(n).intValueExact();
        }
        return BigDecimalType.toBigDecimal(n).intValueExact();
    }

    static int toInt(String s) {
        return Integer.parseInt(s);
    }

}
