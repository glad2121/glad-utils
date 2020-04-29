package org.glad2121.util;

import static org.assertj.core.api.Assertions.*;
import static org.glad2121.util.DateTimeUtils.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link DateTimeUtils} の単体テスト。
 */
class DateTimeUtilsTest {

    static final Clock FIXED_CLOCK =
            ClockHolder.fixedClock("2020-01-23T12:23:34.123456", JST);

    Clock original;

    @BeforeEach
    void setUp() throws Exception {
        original = ClockHolder.get();
    }

    @AfterEach
    void tearDown() throws Exception {
        ClockHolder.setClock(original);
    }

    @Test
    @DisplayName("現在の時点が正しく取得できること。")
    void testNowInstant() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowInstant()).hasToString("2020-01-23T03:23:34.123456Z");
    }

    @Test
    @DisplayName("現在の日付が正しく取得できること。")
    void testToday() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(today()).hasToString("2020-01-23");
    }

    @Test
    @DisplayName("現在のローカル日時が正しく取得できること。")
    void testNowLocalDateTime() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowLocalDateTime())
            .hasToString("2020-01-23T12:23:34.123456");
    }

    @Test
    @DisplayName("現在のタイムゾーン付き日時が正しく取得できること。")
    void testNowZonedDateTime() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowZonedDateTime())
            .hasToString("2020-01-23T12:23:34.123456+09:00[Asia/Tokyo]");
    }

    @Test
    @DisplayName("現在のオフセット付き日時が正しく取得できること。")
    void testNowOffsetDateTime() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowOffsetDateTime())
            .hasToString("2020-01-23T12:23:34.123456+09:00");
    }

    @Test
    @DisplayName("現在の日時が Date で正しく取得できること。")
    void testNowClassicDate() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowClassicDate()).isEqualTo("2020-01-23T12:23:34.123");
    }

    @Test
    @DisplayName("現在の日時が Timestamp で正しく取得できること。")
    void testNowTimestamp() {
        ClockHolder.setClock(FIXED_CLOCK);
        assertThat(nowTimestamp()).hasToString("2020-01-23 12:23:34.123456");
    }

    @Test
    @DisplayName("時点から他の型へ正しく変換できること。")
    void testInstantToOther() {
        Instant instant = Instant.parse("2020-01-23T23:34:45.123456789Z");

        assertThat(toLocalDate(instant, UTC)).hasToString("2020-01-23");
        assertThat(toLocalDate(instant, JST)).hasToString("2020-01-24");

        assertThat(toLocalDateTime(instant, UTC))
            .hasToString("2020-01-23T23:34:45.123456789");
        assertThat(toLocalDateTime(instant, JST))
            .hasToString("2020-01-24T08:34:45.123456789");

        assertThat(toZonedDateTime(instant, UTC))
            .hasToString("2020-01-23T23:34:45.123456789Z");
        assertThat(toZonedDateTime(instant, JST))
            .hasToString("2020-01-24T08:34:45.123456789+09:00[Asia/Tokyo]");

        assertThat(toOffsetDateTime(instant, UTC))
            .hasToString("2020-01-23T23:34:45.123456789Z");
        assertThat(toOffsetDateTime(instant, JST))
            .hasToString("2020-01-24T08:34:45.123456789+09:00");

        assertThat(toClassicDate(instant)).isEqualTo("2020-01-24T08:34:45.123");
        assertThat(toTimestamp(instant)).hasToString("2020-01-24 08:34:45.123456789");
    }

    @Test
    @DisplayName("ローカル日時から他の型へ正しく変換できること。")
    void testLocalDateTimeToOther() {
        LocalDateTime dateTime = LocalDateTime.parse("2020-01-23T12:23:34.123456");

        assertThat(toInstant(dateTime, UTC))
            .hasToString("2020-01-23T12:23:34.123456Z");
        assertThat(toInstant(dateTime, JST))
            .hasToString("2020-01-23T03:23:34.123456Z");

        assertThat(toLocalDate(dateTime)).hasToString("2020-01-23");

        assertThat(toZonedDateTime(dateTime, UTC))
            .hasToString("2020-01-23T12:23:34.123456Z");
        assertThat(toZonedDateTime(dateTime, JST))
            .hasToString("2020-01-23T12:23:34.123456+09:00[Asia/Tokyo]");

        assertThat(toOffsetDateTime(dateTime, UTC))
            .hasToString("2020-01-23T12:23:34.123456Z");
        assertThat(toOffsetDateTime(dateTime, JST9))
            .hasToString("2020-01-23T12:23:34.123456+09:00");
    }

    @Test
    @DisplayName("タイムゾーン付き日時から他の型へ正しく変換できること。")
    void testZonedDateTimeToOther() {
        ZonedDateTime dateTime = ZonedDateTime.of(2020, 1, 23, 12, 23, 34, 123456789, JST);
        assertThat(toInstant(dateTime)).hasToString("2020-01-23T03:23:34.123456789Z");
        assertThat(toLocalDate(dateTime)).hasToString("2020-01-23");
        assertThat(toLocalDateTime(dateTime))
            .hasToString("2020-01-23T12:23:34.123456789");
        assertThat(toOffsetDateTime(dateTime))
            .hasToString("2020-01-23T12:23:34.123456789+09:00");
    }

    @Test
    @DisplayName("オフセット付き日時から他の型へ正しく変換できること。")
    void testOffsetDateTimeToOther() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2020-01-23T12:23:34.123456+09:00");
        assertThat(toInstant(dateTime)).hasToString("2020-01-23T03:23:34.123456Z");
        assertThat(toLocalDate(dateTime)).hasToString("2020-01-23");
        assertThat(toLocalDateTime(dateTime))
            .hasToString("2020-01-23T12:23:34.123456");
        assertThat(toZonedDateTime(dateTime))
            .hasToString("2020-01-23T12:23:34.123456+09:00");
    }

    @Test
    @DisplayName("太平洋時間のサマータイムが正しく変換できること。")
    void testPacificTime() {
        ZoneId PT = ZoneId.of("America/Los_Angeles");

        // サマータイム適用開始前。
        assertThat(toZonedDateTime(Instant.parse("2020-03-08T09:59:59.999999999Z"), PT))
            .hasToString("2020-03-08T01:59:59.999999999-08:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 1, 59, 59, 999999999, PT)))
            .hasToString("2020-03-08T09:59:59.999999999Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 1, 59, 59, 999999999, PT)))
            .hasToString("2020-03-08T01:59:59.999999999-08:00");

        // ローカル日時 2:00-2:59 は存在しない。
        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 2, 0, 0, 0, PT)))
            .hasToString("2020-03-08T10:00:00Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 2, 0, 0, 0, PT)))
            .hasToString("2020-03-08T03:00-07:00");

        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 2, 0, 0, 1, PT)))
            .hasToString("2020-03-08T10:00:00.000000001Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 2, 0, 0, 1, PT)))
            .hasToString("2020-03-08T03:00:00.000000001-07:00");

        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 2, 59, 59, 999999999, PT)))
            .hasToString("2020-03-08T10:59:59.999999999Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 2, 59, 59, 999999999, PT)))
            .hasToString("2020-03-08T03:59:59.999999999-07:00");

        // サマータイム適用開始後（3月第2日曜日 2:00）。
        assertThat(toZonedDateTime(Instant.parse("2020-03-08T10:00:00Z"), PT))
            .hasToString("2020-03-08T03:00-07:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 3, 0, 0, 0, PT)))
            .hasToString("2020-03-08T10:00:00Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 3, 0, 0, 0, PT)))
            .hasToString("2020-03-08T03:00-07:00");

        assertThat(toZonedDateTime(Instant.parse("2020-03-08T10:00:00.000000001Z"), PT))
            .hasToString("2020-03-08T03:00:00.000000001-07:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 3, 8, 3, 0, 0, 1, PT)))
            .hasToString("2020-03-08T10:00:00.000000001Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 3, 8, 3, 0, 0, 1, PT)))
            .hasToString("2020-03-08T03:00:00.000000001-07:00");

        // サマータイム適用終了前。
        assertThat(toZonedDateTime(Instant.parse("2020-11-01T08:00:00Z"), PT))
            .hasToString("2020-11-01T01:00-07:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 11, 1, 1, 0, 0, 0, PT)))
            .hasToString("2020-11-01T08:00:00Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 11, 1, 1, 0, 0, 0, PT)))
            .hasToString("2020-11-01T01:00-07:00");

        assertThat(toZonedDateTime(Instant.parse("2020-11-01T08:00:00.000000001Z"), PT))
            .hasToString("2020-11-01T01:00:00.000000001-07:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 11, 1, 1, 0, 0, 1, PT)))
            .hasToString("2020-11-01T08:00:00.000000001Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 11, 1, 1, 0, 0, 1, PT)))
            .hasToString("2020-11-01T01:00:00.000000001-07:00");

        assertThat(toZonedDateTime(Instant.parse("2020-11-01T08:59:59.999999999Z"), PT))
            .hasToString("2020-11-01T01:59:59.999999999-07:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 11, 1, 1, 59, 59, 999999999, PT)))
            .hasToString("2020-11-01T08:59:59.999999999Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 11, 1, 1, 59, 59, 999999999, PT)))
            .hasToString("2020-11-01T01:59:59.999999999-07:00");

        // サマータイム適用終了後（11月第1日曜日 2:00）。
        // ローカル日時 1:00-1:59 が重複。
        assertThat(toZonedDateTime(Instant.parse("2020-11-01T09:00:00Z"), PT))
            .hasToString("2020-11-01T01:00-08:00[America/Los_Angeles]");

        assertThat(toZonedDateTime(Instant.parse("2020-11-01T09:00:00.000000001Z"), PT))
            .hasToString("2020-11-01T01:00:00.000000001-08:00[America/Los_Angeles]");

        assertThat(toZonedDateTime(Instant.parse("2020-11-01T10:00:00Z"), PT))
            .hasToString("2020-11-01T02:00-08:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 11, 1, 2, 0, 0, 0, PT)))
            .hasToString("2020-11-01T10:00:00Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 11, 1, 2, 0, 0, 0, PT)))
            .hasToString("2020-11-01T02:00-08:00");

        assertThat(toZonedDateTime(Instant.parse("2020-11-01T10:00:00.000000001Z"), PT))
            .hasToString("2020-11-01T02:00:00.000000001-08:00[America/Los_Angeles]");
        assertThat(toInstant(ZonedDateTime.of(2020, 11, 1, 2, 0, 0, 1, PT)))
            .hasToString("2020-11-01T10:00:00.000000001Z");
        assertThat(toOffsetDateTime(ZonedDateTime.of(2020, 11, 1, 2, 0, 0, 1, PT)))
            .hasToString("2020-11-01T02:00:00.000000001-08:00");
    }

    @Test
    @DisplayName("Date から他の型へ正しく変換できること。")
    void testClassicDateToOther() throws Exception {
        Date date = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS")
                .parse("20200123T122334.123");
        assertThat(toInstant(date)).hasToString("2020-01-23T03:23:34.123Z");
    }

    @Test
    @DisplayName("Timestamp から他の型へ正しく変換できること。")
    void testTimestampToOther() {
        Timestamp timestamp = Timestamp.valueOf("2020-01-23 12:23:34.123456789");
        assertThat(toInstant(timestamp)).hasToString("2020-01-23T03:23:34.123456789Z");
    }

}
