package org.glad2121.converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * 時点 ({@code Instant}) 型。
 *
 * @author glad2121
 */
public class InstantType extends ValueType {

    /**
     * オブジェクトを時点に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Instant convert(Object o) {
        return toInstant(o);
    }

    /**
     * オブジェクトを時点に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Instant toInstant(Object o) {
        if (o instanceof Temporal) {
            return toInstant((Temporal) o);
        }
        if (o instanceof Long) {
            return toInstant((long) o);
        }
        if (o instanceof Date) {
            return toInstant((Date) o);
        }
        return toInstant(o.toString());
    }

    /**
     * エポックからのミリ秒数を時点に変換します。
     *
     * @param millis ミリ秒数
     * @return 変換後の値
     */
    static Instant toInstant(long millis) {
        return Instant.ofEpochMilli(millis);
    }

    /**
     * {@code Date} を時点に変換します。
     *
     * @param date {@code Date}
     * @return 変換後の値
     */
    static Instant toInstant(Date date) {
        return date.toInstant();
    }

    /**
     * {@code Temporal} を時点に変換します。
     *
     * @param temporal {@code Temporal}
     * @return 変換後の値
     */
    static Instant toInstant(Temporal temporal) {
        if (temporal instanceof Instant) {
            return (Instant) temporal;
        }
        if (temporal instanceof ChronoLocalDateTime) {
            return toInstant((ChronoLocalDateTime<?>) temporal);
        }
        return Instant.from(temporal);
    }

    /**
     * システムのデフォルトタイムゾーンで、ローカル日時を時点に変換します。
     *
     * @param dateTime ローカル日時
     * @return 変換後の値
     */
    static Instant toInstant(ChronoLocalDateTime<?> dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * オフセット付き日時を表す文字列を時点に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static Instant toInstant(CharSequence s) {
        return OffsetDateTime.parse(s).toInstant();
    }

}
