package org.glad2121.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * ローカル日付 ({@code LocalDate}) 型。
 *
 * @author glad2121
 */
class LocalDateType extends ValueType {

    /**
     * オブジェクトをローカル日付に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public LocalDate convert(Object o) {
        return toLocalDate(o);
    }

    /**
     * オブジェクトをローカル日付に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static LocalDate toLocalDate(Object o) {
        if (o instanceof Temporal) {
            return toLocalDate((Temporal) o);
        }
        if (o instanceof Long) {
            return toLocalDate((long) o);
        }
        if (o instanceof Date) {
            return toLocalDate((Date) o);
        }
        return toLocalDate(o.toString());
    }

    /**
     * エポックからのミリ秒数をシステムのデフォルトタイムゾーンのローカル日付に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static LocalDate toLocalDate(long millis) {
        return toLocalDate(Instant.ofEpochMilli(millis));
    }

    /**
     * {@code Date} をシステムのデフォルトタイムゾーンのローカル日付に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static LocalDate toLocalDate(Date date) {
        return toLocalDate(date.toInstant());
    }

    /**
     * {@code Temporal} をローカル日付に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static LocalDate toLocalDate(Temporal temporal) {
        if (temporal instanceof LocalDate) {
            return (LocalDate) temporal;
        }
        if (temporal instanceof Instant) {
            return toLocalDate((Instant) temporal);
        }
        return LocalDate.from(temporal);
    }

    /**
     * 時点をシステムのデフォルトタイムゾーンのローカル日付に変換します。
     *
     * @param instant 時点
     * @return 変換後の値
     */
    static LocalDate toLocalDate(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 文字列をローカル日付に変換します。
     *
     * @param s 文字列
     * @return ローカル日付
     */
    static LocalDate toLocalDate(CharSequence s) {
        return LocalDate.parse(s);
    }

}
