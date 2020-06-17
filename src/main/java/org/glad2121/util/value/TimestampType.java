package org.glad2121.util.value;

import java.sql.Timestamp;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * {@code Timestamp} 型。
 *
 * @author glad2121
 */
class TimestampType extends ValueType {

    /**
     * オブジェクトを {@code Timestamp} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Timestamp convert(Object o) {
        return toTimestamp(o);
    }

    /**
     * オブジェクトを {@code Timestamp} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Timestamp toTimestamp(Object o) {
        if (o instanceof Date) {
            return toTimestamp((Date) o);
        }
        if (o instanceof Long) {
            return toTimestamp((long) o);
        }
        if (o instanceof Temporal) {
            return toTimestamp((Temporal) o);
        }
        return toTimestamp(String.valueOf(o));
    }

    /**
     * エポックからのミリ秒数を {@code Timestamp} に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static Timestamp toTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * {@code Date} を {@code Timestamp} に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static Timestamp toTimestamp(Date date) {
        if (date instanceof Timestamp) {
            return (Timestamp) date;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * {@code Temporal} を {@code Timestamp} に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static Timestamp toTimestamp(Temporal temporal) {
        return Timestamp.from(InstantType.toInstant(temporal));
    }

    /**
     * 文字列を {@code Timestamp} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static Timestamp toTimestamp(String s) {
        if (s.indexOf('T') >= 0) {
            return toTimestamp(InstantType.toInstant(s));
        }
        return Timestamp.valueOf(s);
    }

}
