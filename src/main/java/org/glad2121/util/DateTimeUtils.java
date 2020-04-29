package org.glad2121.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 日時関連のユーティリティ。
 *
 * @author glad2121
 */
public final class DateTimeUtils {

    /**
     * 協定世界時。
     */
    public static final ZoneOffset UTC = ZoneOffset.UTC;

    /**
     * 日本標準時。
     */
    public static final ZoneId JST = ZoneId.of("Asia/Tokyo");

    /**
     * 時差9時間固定の日本標準時。
     */
    public static final ZoneOffset JST9 = ZoneOffset.ofHours(9);

    /**
     * 使用しないコンストラクタ。
     */
    private DateTimeUtils() {
    }

    /**
     * 現在の時点を返します。
     *
     * @return 現在の時点
     */
    public static Instant nowInstant() {
        return Instant.now(ClockHolder.get());
    }

    /**
     * 現在の日付を返します。
     *
     * @return 現在の日付
     */
    public static LocalDate today() {
        return LocalDate.now(ClockHolder.get());
    }

    /**
     * 現在のローカル日時を返します。
     *
     * @return 現在のローカル日時
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now(ClockHolder.get());
    }

    /**
     * 現在のタイムゾーン付き日時を返します。
     *
     * @return 現在のタイムゾーン付き日時
     */
    public static ZonedDateTime nowZonedDateTime() {
        return ZonedDateTime.now(ClockHolder.get());
    }

    /**
     * 現在のオフセット付き日時を返します。
     *
     * @return 現在のオフセット日時
     */
    public static OffsetDateTime nowOffsetDateTime() {
        return OffsetDateTime.now(ClockHolder.get());
    }

    /**
     * 現在の日時を {@code Date} で返します。
     *
     * @return 現在の日時を表す {@code Date}
     */
    public static Date nowClassicDate() {
        return Date.from(nowInstant());
    }

    /**
     * 現在の日時を {@code Timestamp} で返します。
     *
     * @return 現在の日時を表す {@code Timestamp}
     */
    public static Timestamp nowTimestamp() {
        return Timestamp.from(nowInstant());
    }

    /**
     * ローカル日時を時点に変換します。
     *
     * @param dateTime ローカル日時
     * @param zone タイムゾーン
     * @return 時点
     */
    public static Instant toInstant(LocalDateTime dateTime, ZoneId zone) {
        if (dateTime == null) {
            return null;
        }
        return ZonedDateTime.of(dateTime, zone).toInstant();
    }

    /**
     * タイムゾーン付き日時を時点に変換します。
     *
     * @param dateTime タイムゾーン付き日時
     * @return 時点
     */
    public static Instant toInstant(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant();
    }

    /**
     * オフセット付き日時を時点に変換します。
     *
     * @param dateTime オフセット付き日時
     * @return 時点
     */
    public static Instant toInstant(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant();
    }

    /**
     * {@code Date} を時点に変換します。
     *
     * @param date {@code Date}
     * @return 時点
     */
    public static Instant toInstant(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant();
    }

    /**
     * 時点をローカル日付に変換します。
     *
     * @param instant 時点
     * @param zone タイムゾーン
     * @return ローカル日付
     */
    public static LocalDate toLocalDate(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return LocalDate.ofInstant(instant, zone);
    }

    /**
     * ローカル日時をローカル日付に変換します。
     *
     * @param dateTime ローカル日時
     * @return ローカル日付
     */
    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDate();
    }

    /**
     * タイムゾーン付き日時をローカル日付に変換します。
     *
     * @param dateTime タイムゾーン付き日時
     * @return ローカル日付
     */
    public static LocalDate toLocalDate(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDate();
    }

    /**
     * オフセット付き日時をローカル日付に変換します。
     *
     * @param dateTime オフセット付き日時
     * @return ローカル日付
     */
    public static LocalDate toLocalDate(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDate();
    }

    /**
     * {@code Date} をローカル日付に変換します。
     *
     * @param date {@code Date}
     * @param zone タイムゾーン
     * @return ローカル日付
     */
    public static LocalDate toLocalDate(Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }
        return LocalDate.ofInstant(date.toInstant(), zone);
    }

    /**
     * 時点をローカル日時に変換します。
     *
     * @param instant 時点
     * @param zone タイムゾーン
     * @return ローカル日時
     */
    public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * タイムゾーン付き日時をローカル日時に変換します。
     *
     * @param dateTime タイムゾーン付き日時
     * @return ローカル日時
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDateTime();
    }

    /**
     * オフセット付き日時をローカル日時に変換します。
     *
     * @param dateTime オフセット付き日時
     * @return ローカル日時
     */
    public static LocalDateTime toLocalDateTime(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toLocalDateTime();
    }

    /**
     * {@code Date} をローカル日時に変換します。
     *
     * @param date {@code Date}
     * @param zone タイムゾーン
     * @return ローカル日時
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    /**
     * 時点をタイムゾーン付き日時に変換します。
     *
     * @param instant 時点
     * @param zone タイムゾーン
     * @return タイムゾーン付き日時
     */
    public static ZonedDateTime toZonedDateTime(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(instant, zone);
    }

    /**
     * ローカル日時をタイムゾーン付き日時に変換します。
     *
     * @param dateTime ローカル日時
     * @param zone タイムゾーン
     * @return タイムゾーン付き日時
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime dateTime, ZoneId zone) {
        if (dateTime == null) {
            return null;
        }
        return ZonedDateTime.of(dateTime, zone);
    }

    /**
     * オフセット付き日時をタイムゾーン付き日時に変換します。
     *
     * @param dateTime オフセット付き日時
     * @return タイムゾーン付き日時
     */
    public static ZonedDateTime toZonedDateTime(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return ZonedDateTime.from(dateTime);
    }

    /**
     * {@code Date} をタイムゾーン付き日時に変換します。
     *
     * @param date {@code Date}
     * @param zone タイムゾーン
     * @return タイムゾーン付き日時
     */
    public static ZonedDateTime toZonedDateTime(Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(date.toInstant(), zone);
    }

    /**
     * 時点をオフセット付き日時に変換します。
     *
     * @param instant 時点
     * @param zone タイムゾーン
     * @return オフセット付き日時
     */
    public static OffsetDateTime toOffsetDateTime(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, zone);
    }

    /**
     * ローカル日時をオフセット付き日時に変換します。
     *
     * @param dateTime ローカル日時
     * @param offset オフセット
     * @return オフセット付き日時
     */
    public static OffsetDateTime toOffsetDateTime(LocalDateTime dateTime, ZoneOffset offset) {
        if (dateTime == null) {
            return null;
        }
        return OffsetDateTime.of(dateTime, offset);
    }

    /**
     * タイムゾーン付き日時をオフセット付き日時に変換します。
     *
     * @param dateTime タイムゾーン付き日時
     * @return オフセット付き日時
     */
    public static OffsetDateTime toOffsetDateTime(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return OffsetDateTime.from(dateTime);
    }

    /**
     * {@code Date} をオフセット付き日時に変換します。
     *
     * @param date {@code Date}
     * @param zone タイムゾーン
     * @return オフセット付き日時
     */
    public static OffsetDateTime toOffsetDateTime(Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(date.toInstant(), zone);
    }

    /**
     * 時点を {@code Date} に変換します。
     *
     * @param instant 時点
     * @return {@code Date}
     */
    public static Date toClassicDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return Date.from(instant);
    }

    /**
     * 時点を {@code Timestamp} に変換します。
     *
     * @param instant 時点
     * @return {@code Timestamp}
     */
    public static Timestamp toTimestamp(Instant instant) {
        if (instant == null) {
            return null;
        }
        return Timestamp.from(instant);
    }

}
