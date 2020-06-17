package org.glad2121.util.value;

import java.math.BigInteger;

/**
 * {@code byte / Byte} 型。
 *
 * @author glad2121
 */
class ByteType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    ByteType(Byte defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Byte} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Byte convert(Object o) {
        return toByte(o);
    }

    /**
     * オブジェクトを {@code Byte} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
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
        return toByte(String.valueOf(o));
    }

    /**
     * {@code boolean} を {@code byte} に変換します。
     *
     * @param b {@code boolean}
     * @return 変換後の値
     */
    static byte toByte(boolean b) {
        return (byte) IntType.toInt(b);
    }

    /**
     * 文字を {@code byte} に変換します。
     *
     * @param c 文字
     * @return 変換後の値
     */
    static byte toByte(char c) {
        if ((c & 0xFF00) == 0) {
            return (byte) c;
        }
        throw new IllegalArgumentException(String.format("¥¥u%04X [%s]", c, c));
    }

    /**
     * 数値を {@code Byte} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
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

    /**
     * 文字列を {@code byte} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static byte toByte(String s) {
        return Byte.parseByte(s);
    }

}
