package org.glad2121.converter;

import java.math.BigInteger;

class ByteType extends ValueType {

    ByteType(Byte defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Byte convert(Object o) {
        return toByte(o);
    }

    static Byte toByte(Object o) {
        if (o instanceof Number) {
            return toByte((Number) o);
        }
        if (o instanceof Boolean) {
            return toByte((boolean) o);
        }
        if (o instanceof Character) {
            return toByte((char) o);
        }
        return toByte(o.toString());
    }

    static byte toByte(boolean b) {
        return (byte) IntType.toInt(b);
    }

    static byte toByte(char c) {
        if ((c & 0xFFFF) < 0x100) {
            return (byte) c;
        }
        throw new IllegalArgumentException(String.format("¥¥u%04X [%s]", c, c));
    }

    static Byte toByte(Number n) {
        if (n instanceof Byte) {
            return (Byte) n;
        }
        if (n instanceof Short || n instanceof Integer || n instanceof Long
                || n instanceof BigInteger) {
            return BigIntegerType.toBigInteger(n).byteValueExact();
        }
        return BigDecimalType.toBigDecimal(n).byteValueExact();
    }

    static byte toByte(String s) {
        return Byte.parseByte(s);
    }

}
