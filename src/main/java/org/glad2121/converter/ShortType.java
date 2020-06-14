package org.glad2121.converter;

import java.math.BigInteger;

class ShortType extends ValueType {

    ShortType(Short defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Short convert(Object o) {
        return toShort(o);
    }

    static Short toShort(Object o) {
        if (o instanceof Number) {
            return toShort((Number) o);
        }
        if (o instanceof Boolean) {
            return toShort((boolean) o);
        }
        if (o instanceof Character) {
            return toShort((char) o);
        }
        return toShort(o.toString());
    }

    static short toShort(boolean b) {
        return (short) IntType.toInt(b);
    }

    static short toShort(char c) {
        return (short) c;
    }

    static Short toShort(Number n) {
        if (n instanceof Short) {
            return (Short) n;
        }
        if (n instanceof Byte) {
            return n.shortValue();
        }
        if (n instanceof Integer || n instanceof Long || n instanceof BigInteger) {
            return BigIntegerType.toBigInteger(n).shortValueExact();
        }
        return BigDecimalType.toBigDecimal(n).shortValueExact();
    }

    static short toShort(String s) {
        return Short.parseShort(s);
    }

}
