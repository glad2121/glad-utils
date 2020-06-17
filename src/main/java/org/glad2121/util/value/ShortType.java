package org.glad2121.util.value;

import java.math.BigInteger;

/**
 * {@code Short} 型。
 *
 * @author glad2121
 */
class ShortType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    ShortType(Short defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Short} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Short convert(Object o) {
        return toShort(o);
    }

    /**
     * オブジェクトを {@code Short} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
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
        return toShort(String.valueOf(o));
    }

    /**
     * {@code boolean} を {@code short} に変換します。
     *
     * @param b {@code boolean}
     * @return 変換後の値
     */
    static short toShort(boolean b) {
        return (short) IntType.toInt(b);
    }

    /**
     * 文字を {@code short} に変換します。
     *
     * @param c 文字
     * @return 変換後の値
     */
    static short toShort(char c) {
        return (short) c;
    }

    /**
     * 数値を {@code SHort} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
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

    /**
     * 文字列を {@code short} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static short toShort(String s) {
        return Short.parseShort(s);
    }

}
