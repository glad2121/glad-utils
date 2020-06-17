package org.glad2121.util.value;

import java.math.BigInteger;

/**
 * {@code int / Integer} 型
 *
 * @author glad2121
 */
class IntType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    IntType(Integer defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Integer} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Integer convert(Object o) {
        return toInteger(o);
    }

    /**
     * オブジェクトを {@code Integer} に変換します。
     */
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
        return toInt(String.valueOf(o));
    }

    /**
     * {@code boolean} を {@code int} に変換します。
     * <p>
     * {@code true} ならば {@code 1}、{code false} ならば {@code 0} を返す。
     *
     * @param b {@code boolean}
     * @return 変換後の値
     */
    static int toInt(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * 文字を {@code int} に変換します。
     * <p>
     * 正の値 (0x0000 〜 0xFFFF) となるように調整する。
     *
     * @param c 文字
     * @return 変換後の値
     */
    static int toInt(char c) {
        return (int) c;
    }

    /**
     * 数値を {@code Integer} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
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

    /**
     * 文字列を {@code int} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static int toInt(String s) {
        return Integer.parseInt(s);
    }

}
