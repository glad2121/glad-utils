package org.glad2121.util.value;

import java.time.temporal.Temporal;
import java.util.Date;

/**
 * {@code Date} 型。
 *
 * @author glad2121
 */
class DateType extends ValueType {

    /**
     * オブジェクトを {@code Date} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Date convert(Object o) {
        return toDate(o);
    }

    /**
     * オブジェクトを {@code Date} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Date toDate(Object o) {
        if (o instanceof Date) {
            return (Date) o;
        }
        if (o instanceof Long) {
            return toDate((long) o);
        }
        if (o instanceof Temporal) {
            return toDate((Temporal) o);
        }
        return toDate(String.valueOf(o));
    }

    /**
     * エポックからのミリ秒数を {@code Date} に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static Date toDate(long millis) {
        return new Date(millis);
    }

    /**
     * {@code Temporal} を {@code Date} に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static Date toDate(Temporal temporal) {
        return Date.from(InstantType.toInstant(temporal));
    }

    /**
     * 文字列を {@code Date} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static Date toDate(String s) {
        return Date.from(InstantType.toInstant(s));
    }

}
