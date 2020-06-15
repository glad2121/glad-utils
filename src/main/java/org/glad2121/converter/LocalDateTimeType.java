package org.glad2121.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * ローカル日時 ({@code LocalDateTime}) 型。
 *
 * @author glad2121
 */
class LocalDateTimeType extends ValueType {

    /**
     * オブジェクトをローカル日時に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public LocalDateTime convert(Object o) {
        return toLocalDateTime(o);
    }

    /**
     * オブジェクトをローカル日時に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(Object o) {
        if (o instanceof Temporal) {
            return toLocalDateTime((Temporal) o);
        }
        if (o instanceof Long) {
            return toLocalDateTime((long) o);
        }
        if (o instanceof Date) {
            return toLocalDateTime((Date) o);
        }
        return toLocalDateTime(o.toString());
    }

    /**
     * エポックからのミリ秒数をシステムのデフォルトタイムゾーンのローカル日時に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(long millis) {
        return toLocalDateTime(Instant.ofEpochMilli(millis));
    }

    /**
     * {@code Date} をシステムのデフォルトタイムゾーンのローカル日時に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date.toInstant());
    }

    /**
     * {@code Temporal} をローカル日時に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(Temporal temporal) {
        if (temporal instanceof LocalDateTime) {
            return (LocalDateTime) temporal;
        }
        if (temporal instanceof Instant) {
            return toLocalDateTime((Instant) temporal);
        }
        return LocalDateTime.from(temporal);
    }

    /**
     * 時点をシステムのデフォルトタイムゾーンのローカル日時に変換します。
     *
     * @param instant 時点
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 文字列をローカル日時に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static LocalDateTime toLocalDateTime(CharSequence s) {
        return LocalDateTime.parse(s);
    }

}
