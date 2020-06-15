package org.glad2121.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * タイムゾーン付き日時 ({@code ZonedDateTime}) 型。
 *
 * @author glad2121
 */
public class ZonedDateTimeType extends ValueType {

    /**
     * オブジェクトをタイムゾーン付き日時に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public ZonedDateTime convert(Object o) {
        return toZonedDateTime(o);
    }

    /**
     * オブジェクトをタイムゾーン付き日時に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(Object o) {
        if (o instanceof Temporal) {
            return toZonedDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toZonedDateTime((long) o);
        }
        if (o instanceof Date) {
            return toZonedDateTime((Date) o);
        }
        return toZonedDateTime(o.toString());
    }

    /**
     * エポックからのミリ秒数をシステムのデフォルトタイムゾーンの日時に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(long millis) {
        return toZonedDateTime(Instant.ofEpochMilli(millis));
    }

    /**
     * {@code Date} をシステムのデフォルトタイムゾーンの日時に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(Date date) {
        return toZonedDateTime(date.toInstant());
    }

    /**
     * {@code Temporal} をタイムゾーン付き日時に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(Temporal temporal) {
        if (temporal instanceof ZonedDateTime) {
            return (ZonedDateTime) temporal;
        }
        if (temporal instanceof Instant) {
            return toZonedDateTime((Instant) temporal);
        }
        if (temporal instanceof ChronoLocalDateTime) {
            return toZonedDateTime((ChronoLocalDateTime<?>) temporal);
        }
        return ZonedDateTime.from(temporal);
    }

    /**
     * 時点をシステムのデフォルトタイムゾーンの日時に変換します。
     *
     * @param instant 時点
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * ローカル日時をシステムのデフォルトタイムゾーンの日時に変換します。
     *
     * @param dateTime ローカル日時
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(ChronoLocalDateTime<?> dateTime) {
        return ZonedDateTime.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    /**
     * 文字列をタイムゾーン付き日時に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static ZonedDateTime toZonedDateTime(CharSequence s) {
        return ZonedDateTime.parse(s);
    }

}
