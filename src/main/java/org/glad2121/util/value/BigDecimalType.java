package org.glad2121.util.value;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * {@code BigDecimal} 型。
 *
 * @author glad2121
 */
class BigDecimalType extends ValueType {

    /**
     * オブジェクトを {@code BigDecimal} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public BigDecimal convert(Object o) {
        return toBigDecimal(o);
    }

    /**
     * オブジェクトを {@code BigDecimal} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static BigDecimal toBigDecimal(Object o) {
        if (o instanceof Number) {
            return toBigDecimal((Number) o);
        }
        return toBigDecimal(String.valueOf(o));
    }

    /**
     * 数値を {@code BigDecimal} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static BigDecimal toBigDecimal(Number n) {
        if (n instanceof BigDecimal) {
            return (BigDecimal) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer || n instanceof Long) {
            return BigDecimal.valueOf(n.longValue());
        }
        if (n instanceof Float || n instanceof Double) {
            return BigDecimal.valueOf(n.doubleValue());
        }
        if (n instanceof BigInteger) {
            return new BigDecimal((BigInteger) n);
        }
        return toBigDecimal(String.valueOf(n));
    }

    /**
     * 文字列を {@code BigDecimal} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static BigDecimal toBigDecimal(String s) {
        return new BigDecimal(s);
    }

}
