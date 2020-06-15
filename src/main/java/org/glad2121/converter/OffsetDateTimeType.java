package org.glad2121.converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * オフセット付き日時 ({@code OffsetDateTime}) 型。
 *
 * @author glad2121
 */
class OffsetDateTimeType extends ValueType {

    /**
     * オブジェクトをオフセット付き日時に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public OffsetDateTime convert(Object o) {
        return toOffsetDateTime(o);
    }

    /**
     * オブジェクトをオフセット付き日時に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(Object o) {
        if (o instanceof Temporal) {
            return toOffsetDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toOffsetDateTime((long) o);
        }
        if (o instanceof Date) {
            return toOffsetDateTime((Date) o);
        }
        return toOffsetDateTime(o.toString());
    }

    /**
     * エポックからのミリ秒数を協定世界時 (UTC) のオフセット付き日時に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(long millis) {
        return toOffsetDateTime(Instant.ofEpochMilli(millis));
    }

    /**
     * {@code Date} を協定世界時 (UTC) のオフセット付き日時に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(Date date) {
        return toOffsetDateTime(date.toInstant());
    }

    /**
     * {@code Temporal} をオフセット付き日時に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(Temporal temporal) {
        if (temporal instanceof OffsetDateTime) {
            return (OffsetDateTime) temporal;
        }
        if (temporal instanceof Instant) {
            return toOffsetDateTime((Instant) temporal);
        }
        if (temporal instanceof ChronoLocalDateTime) {
            return toOffsetDateTime((ChronoLocalDateTime<?>) temporal);
        }
        return OffsetDateTime.from(temporal);
    }

    /**
     * 時点を協定世界時 (UTC) のオフセット付き日時に変換します。
     *
     * @param instant 時点
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    /**
     * ローカル日時をシステムのデフォルトタイムゾーンのオフセット付き日時に変換します。
     *
     * @param dateTime ローカル日時
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(ChronoLocalDateTime<?> dateTime) {
        return OffsetDateTime.from(dateTime.atZone(ZoneId.systemDefault()));
    }

    /**
     * 文字列をオフセット付き日時に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static OffsetDateTime toOffsetDateTime(CharSequence s) {
        return OffsetDateTime.parse(s);
    }

}
