package org.glad2121.util.value;

import java.math.BigInteger;

/**
 * {@code BigInteger} 型。
 *
 * @author glad2121
 */
class BigIntegerType extends ValueType {

    /**
     * オブジェクトを {@code BigInteger} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public BigInteger convert(Object o) {
        return toBigInteger(o);
    }

    /**
     * オブジェクトを {@code BigInteger} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static BigInteger toBigInteger(Object o) {
        if (o instanceof Number) {
            return toBigInteger((Number) o);
        }
        return toBigInteger(String.valueOf(o));
    }

    /**
     * 数値を {@code BigInteger} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static BigInteger toBigInteger(Number n) {
        if (n instanceof BigInteger) {
            return (BigInteger) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof Long) {
            return BigInteger.valueOf(n.longValue());
        }
        return BigDecimalType.toBigDecimal(n).toBigIntegerExact();
    }

    /**
     * 文字列を {@code BigInteger} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static BigInteger toBigInteger(String s) {
        return new BigInteger(s);
    }

}
