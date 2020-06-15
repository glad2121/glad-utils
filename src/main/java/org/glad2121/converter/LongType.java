package org.glad2121.converter;

import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * {@code long / Long} 型。
 *
 * @author glad2121
 */
class LongType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    public LongType(Long defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Long} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Long convert(Object o) {
        return toLong(o);
    }

    /**
     * オブジェクトを {@code Long} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Long toLong(Object o) {
        if (o instanceof Number) {
            return toLong((Number) o);
        }
        if (o instanceof Boolean) {
            return toLong((boolean) o);
        }
        if (o instanceof Character) {
            return toLong((char) o);
        }
        if (o instanceof Date) {
            return toLong((Date) o);
        }
        if (o instanceof Temporal) {
            return toLong((Temporal) o);
        }
        return toLong(o.toString());
    }

    /**
     * {@code boolean} を {@code long} に変換します。
     * <p>
     * {@code true} ならば {@code 1}、{code false} ならば {@code 0} を返す。
     *
     * @param b {@code boolean}
     * @return 変換後の値
     */
    static long toLong(boolean b) {
        return IntType.toInt(b);
    }

    /**
     * 文字を {@code boolean} に変換します。
     * <p>
     * 正の値 (0x0000 〜 0xFFFF) となるように調整する。
     *
     * @param c 文字
     * @return {@code boolean}
     */
    static long toLong(char c) {
        return IntType.toInt(c);
    }

    /**
     * 数値を {@code Long} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static Long toLong(Number n) {
        if (n instanceof Long) {
            return (Long) n;
        }
        if (n instanceof Byte || n instanceof Short || n instanceof Integer) {
            return n.longValue();
        }
        if (n instanceof BigInteger) {
            return BigInteger.class.cast(n).longValueExact();
        }
        return BigDecimalType.toBigDecimal(n).longValueExact();
    }

    /**
     * {@code Date} をエポックからのミリ秒数に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static long toLong(Date date) {
        return date.getTime();
    }

    /**
     * {@code Temporal} をエポックからのミリ秒数に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static long toLong(Temporal temporal) {
        return InstantType.toInstant(temporal).toEpochMilli();
    }

    /**
     * 文字列を {@code long} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static long toLong(String s) {
        return Long.parseLong(s);
    }

}
