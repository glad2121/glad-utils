package org.glad2121.util.value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

import org.glad2121.util.ClockHolder;

/**
 * 文字列型。
 *
 * @author glad2121
 */
class StringType extends ValueType {

    /**
     * オブジェクトを文字列に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public String convert(Object o) {
        return toString(o);
    }

    /**
     * オブジェクトを文字列に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static String toString(Object o) {
        if (o.getClass() == Date.class) {
            return toString((Date) o);
        }
        if (o instanceof BigDecimal) {
            return toString((BigDecimal) o);
        }
        return o.toString();
    }

    /**
     * {@code Date} を文字列に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static String toString(Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ClockHolder.get().getZone()).toString();
    }

    /**
     * {@code BigDecimal} を文字列に変換します。
     *
     * @param n {@code BigDecimal}
     * @return 変換後の値
     */
    static String toString(BigDecimal n) {
        return n.toPlainString();
    }

}
