package org.glad2121.util;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ValueConverter} の単体テスト。
 */
class ValueConverterTest {

    Clock originalClock;

    @BeforeEach
    void setUp() throws Exception {
        originalClock = ClockHolder.get();
        ClockHolder.setClock(Clock.system(DateTimeUtils.JST));
    }

    @AfterEach
    void tearDown() throws Exception {
        ClockHolder.setClock(originalClock);
    }

    @Test
    @DisplayName("boolean への変換が正しく動作すること。")
    void testBoolean() {
        // null の変換。
        assertThat(ValueConverter.convert(null, boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(null, boolean.class, true)).isEqualTo(true);

        // boolean からの変換。
        assertThat(ValueConverter.convert(false, boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(true, boolean.class)).isEqualTo(true);

        // 文字からの変換。
        assertThat(ValueConverter.convert('T', boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('y', boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('1', boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('F', boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert('0', boolean.class)).isEqualTo(false);

        // 数値からの変換。
        assertThat(ValueConverter.convert(0, boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(123, boolean.class)).isEqualTo(true);
        assertThatThrownBy(() -> ValueConverter.convert(123.0, boolean.class))
            .isInstanceOf(IllegalArgumentException.class);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("TRUE", boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("Yes", boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("on", boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("1", boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("FALSE", boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("No", boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("off", boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("0", boolean.class)).isEqualTo(false);

        // Optional からの変換。
        assertThat(ValueConverter.convert(Optional.empty(), boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(Optional.of("yes"), boolean.class)).isEqualTo(true);

        // Optional への変換。
        assertThat(ValueConverter.toOptional(null, boolean.class)).isEmpty();
        assertThat(ValueConverter.toOptional(null, boolean.class).orElse(true)).isEqualTo(true);
    }

    @Test
    @DisplayName("Boolean への変換が正しく動作すること。")
    void testBooleanObj() {
        // null の変換。
        assertThat(ValueConverter.convert(null, Boolean.class)).isNull();
        assertThat(ValueConverter.convert(null, Boolean.class, true)).isEqualTo(true);

        // boolean からの変換。
        assertThat(ValueConverter.convert(false, Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(true, Boolean.class)).isEqualTo(true);

        // 文字からの変換。
        assertThat(ValueConverter.convert('T', Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('y', Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('1', Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert('F', Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert('0', Boolean.class)).isEqualTo(false);

        // 数値からの変換。
        assertThat(ValueConverter.convert(0, Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert(123, Boolean.class)).isEqualTo(true);
        assertThatThrownBy(() -> ValueConverter.convert(123.0, Boolean.class))
            .isInstanceOf(IllegalArgumentException.class);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("TRUE", Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("Yes", Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("on", Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("1", Boolean.class)).isEqualTo(true);
        assertThat(ValueConverter.convert("FALSE", Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("No", Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("off", Boolean.class)).isEqualTo(false);
        assertThat(ValueConverter.convert("0", Boolean.class)).isEqualTo(false);

        // Optional からの変換。
        assertThat(ValueConverter.convert(Optional.empty(), Boolean.class)).isNull();
        assertThat(ValueConverter.convert(Optional.of("yes"), Boolean.class)).isEqualTo(true);

        // Optional への変換。
        assertThat(ValueConverter.toOptional(null, Boolean.class)).isEmpty();
        assertThat(ValueConverter.toOptional(null, Boolean.class).orElse(true)).isEqualTo(true);
    }

    @Test
    @DisplayName("int への変換が正しく動作すること。")
    void testInt() {
        // null の変換。
        assertThat(ValueConverter.convert(null, int.class)).isEqualTo(0);
        assertThat(ValueConverter.convert(null, int.class, -1)).isEqualTo(-1);

        // boolean からの変換。
        assertThat(ValueConverter.convert(false, int.class)).isEqualTo(0);
        assertThat(ValueConverter.convert(true, int.class)).isEqualTo(1);

        // 文字からの変換。
        assertThat(ValueConverter.convert('\0', int.class)).isEqualTo(0);
        assertThat(ValueConverter.convert('A', int.class)).isEqualTo(0x41);
        assertThat(ValueConverter.convert('あ', int.class)).isEqualTo(0x3042);

        // 数値からの変換。
        assertThat(ValueConverter.convert(123, int.class)).isEqualTo(123);
        assertThat(ValueConverter.convert(123L, int.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert(12345678901L, int.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(123.0, int.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert(123.4, int.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(new BigDecimal("123"), int.class)).isEqualTo(123);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("123", int.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert("12345678901", int.class))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ValueConverter.convert("123.4", int.class))
            .isInstanceOf(NumberFormatException.class);

        // Optional からの変換。
        assertThat(ValueConverter.convert(Optional.empty(), int.class)).isEqualTo(0);
        assertThat(ValueConverter.convert(Optional.of("123"), int.class)).isEqualTo(123);

        // Optional への変換。
        assertThat(ValueConverter.toOptional(null, int.class)).isEmpty();
        assertThat(ValueConverter.toOptional(null, int.class).orElse(-1)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Integer への変換が正しく動作すること。")
    void testInteger() {
        // null の変換。
        assertThat(ValueConverter.convert(null, Integer.class)).isNull();
        assertThat(ValueConverter.convert(null, Integer.class, -1)).isEqualTo(-1);

        // boolean からの変換。
        assertThat(ValueConverter.convert(false, Integer.class)).isEqualTo(0);
        assertThat(ValueConverter.convert(true, Integer.class)).isEqualTo(1);

        // 文字からの変換。
        assertThat(ValueConverter.convert('\0', Integer.class)).isEqualTo(0);
        assertThat(ValueConverter.convert('A', Integer.class)).isEqualTo(0x41);
        assertThat(ValueConverter.convert('あ', Integer.class)).isEqualTo(0x3042);

        // 数値からの変換。
        assertThat(ValueConverter.convert(123, Integer.class)).isEqualTo(123);
        assertThat(ValueConverter.convert(123L, Integer.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert(12345678901L, Integer.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(123.0, Integer.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert(123.4, Integer.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(new BigDecimal("123"), Integer.class)).isEqualTo(123);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("123", Integer.class)).isEqualTo(123);
        assertThatThrownBy(() -> ValueConverter.convert("12345678901", Integer.class))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ValueConverter.convert("123.4", Integer.class))
            .isInstanceOf(NumberFormatException.class);

        // Optional からの変換。
        assertThat(ValueConverter.convert(Optional.empty(), Integer.class)).isNull();
        assertThat(ValueConverter.convert(Optional.of("123"), Integer.class)).isEqualTo(123);

        // Optional への変換。
        assertThat(ValueConverter.toOptional(null, Integer.class)).isEmpty();
        assertThat(ValueConverter.toOptional(null, Integer.class).orElse(-1)).isEqualTo(-1);
    }

    @Test
    @DisplayName("long への変換が正しく動作すること。")
    void testLong() {
        // null の変換。
        assertThat(ValueConverter.convert(null, long.class)).isEqualTo(0L);
        assertThat(ValueConverter.convert(null, long.class, -1L)).isEqualTo(-1L);

        // 数値からの変換。
        assertThat(ValueConverter.convert(123, long.class)).isEqualTo(123L);
        assertThat(ValueConverter.convert(123L, long.class)).isEqualTo(123L);
        assertThat(ValueConverter.convert(12345678901L, long.class)).isEqualTo(12345678901L);
        assertThat(ValueConverter.convert(123.0, long.class)).isEqualTo(123L);
        assertThatThrownBy(() -> ValueConverter.convert(123.4, long.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(new BigDecimal("123"), long.class)).isEqualTo(123L);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("123", long.class)).isEqualTo(123L);
        assertThatThrownBy(() -> ValueConverter.convert("12345678901234567890", long.class))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ValueConverter.convert("123.4", long.class))
            .isInstanceOf(NumberFormatException.class);

        // 日時からの変換。
        assertThat(ValueConverter.convert(Instant.parse(
                "1970-01-01T00:00:00Z"), long.class)).isEqualTo(0L);
        assertThat(ValueConverter.convert(OffsetDateTime.parse(
                "2020-01-12T12:23:34.123+09:00"), long.class)).isEqualTo(1578799414123L);
    }

    @Test
    @DisplayName("Long への変換が正しく動作すること。")
    void testLongObj() {
        // null の変換。
        assertThat(ValueConverter.convert(null, Long.class)).isEqualTo(null);
        assertThat(ValueConverter.convert(null, Long.class, -1L)).isEqualTo(-1L);

        // 数値からの変換。
        assertThat(ValueConverter.convert(123, Long.class)).isEqualTo(123L);
        assertThat(ValueConverter.convert(123L, Long.class)).isEqualTo(123L);
        assertThat(ValueConverter.convert(12345678901L, Long.class)).isEqualTo(12345678901L);
        assertThat(ValueConverter.convert(123.0, Long.class)).isEqualTo(123L);
        assertThatThrownBy(() -> ValueConverter.convert(123.4, Long.class))
            .isInstanceOf(ArithmeticException.class);
        assertThat(ValueConverter.convert(new BigDecimal("123"), Long.class)).isEqualTo(123L);

        // 文字列からの変換。
        assertThat(ValueConverter.convert("123", Long.class)).isEqualTo(123L);
        assertThatThrownBy(() -> ValueConverter.convert("12345678901234567890", Long.class))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ValueConverter.convert("123.4", Long.class))
            .isInstanceOf(NumberFormatException.class);

        // 日時からの変換。
        assertThat(ValueConverter.convert(Instant.parse(
                "1970-01-01T00:00:00Z"), Long.class)).isEqualTo(0L);
        assertThat(ValueConverter.convert(OffsetDateTime.parse(
                "2020-01-12T12:23:34.123+09:00"), Long.class)).isEqualTo(1578799414123L);
    }

    @Test
    @DisplayName("Instant への変換が正しく動作すること。")
    void testInstant() {
        Instant now = Instant.now();
        OffsetDateTime odt = OffsetDateTime.parse("2020-01-12T12:23:34.123456+09:00");
        ZonedDateTime zdt = ZonedDateTime.of(odt.toLocalDateTime(), DateTimeUtils.JST);

        assertThat(ValueConverter.convert(null, Instant.class)).isNull();
        assertThat(ValueConverter.convert(null, Instant.class, now)).isSameAs(now);

        assertThat(ValueConverter.convert(now.toEpochMilli(), Instant.class))
            .isEqualTo(now.truncatedTo(ChronoUnit.MILLIS));

        assertThat(ValueConverter.convert(Date.from(now), Instant.class))
            .isEqualTo(now.truncatedTo(ChronoUnit.MILLIS));
        assertThat(ValueConverter.convert(Timestamp.from(now), Instant.class)).isEqualTo(now);
        assertThat(ValueConverter.convert(now, Instant.class)).isSameAs(now);
        assertThat(ValueConverter.convert(odt, Instant.class))
            .isEqualTo(Instant.parse("2020-01-12T03:23:34.123456Z"));
        assertThat(ValueConverter.convert(zdt, Instant.class))
            .isEqualTo(Instant.parse("2020-01-12T03:23:34.123456Z"));
        assertThat(ValueConverter.convert(odt.toLocalDateTime(), Instant.class))
            .isEqualTo(Instant.parse("2020-01-12T03:23:34.123456Z"));
    }

    @Test
    @DisplayName("OffsetDateTime への変換が正しく動作すること。")
    void testOffsetDateTime() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime odt = OffsetDateTime.parse("2020-01-12T12:23:34.123456+09:00");
        ZonedDateTime zdt = ZonedDateTime.of(odt.toLocalDateTime(), DateTimeUtils.JST);

        assertThat(ValueConverter.convert(null, OffsetDateTime.class)).isNull();
        assertThat(ValueConverter.convert(null, OffsetDateTime.class, now)).isSameAs(now);

        assertThat(ValueConverter.convert(odt.toInstant().toEpochMilli(), OffsetDateTime.class))
            .hasToString("2020-01-12T03:23:34.123Z");

        assertThat(ValueConverter.convert(Date.from(odt.toInstant()), OffsetDateTime.class))
            .hasToString("2020-01-12T03:23:34.123Z");
        assertThat(ValueConverter.convert(Timestamp.from(odt.toInstant()), OffsetDateTime.class))
            .hasToString("2020-01-12T03:23:34.123456Z");
        assertThat(ValueConverter.convert(odt.toInstant(), OffsetDateTime.class))
            .hasToString("2020-01-12T03:23:34.123456Z");
        assertThat(ValueConverter.convert(now, OffsetDateTime.class)).isSameAs(now);
        assertThat(ValueConverter.convert(zdt, OffsetDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00");
        assertThat(ValueConverter.convert(odt.toLocalDateTime(), OffsetDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00");
    }

    @Test
    @DisplayName("ZonedDateTime への変換が正しく動作すること。")
    void testZonedDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        OffsetDateTime odt = OffsetDateTime.parse("2020-01-12T12:23:34.123456+09:00");

        assertThat(ValueConverter.convert(null, ZonedDateTime.class)).isNull();
        assertThat(ValueConverter.convert(null, ZonedDateTime.class, now)).isSameAs(now);

        assertThat(ValueConverter.convert(odt.toInstant().toEpochMilli(), ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123+09:00[Asia/Tokyo]");

        assertThat(ValueConverter.convert(Date.from(odt.toInstant()), ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123+09:00[Asia/Tokyo]");
        assertThat(ValueConverter.convert(Timestamp.from(odt.toInstant()), ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00[Asia/Tokyo]");
        assertThat(ValueConverter.convert(odt.toInstant(), ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00[Asia/Tokyo]");
        assertThat(ValueConverter.convert(odt, ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00");
        assertThat(ValueConverter.convert(now, ZonedDateTime.class)).isSameAs(now);
        assertThat(ValueConverter.convert(odt.toLocalDateTime(), ZonedDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456+09:00[Asia/Tokyo]");
    }

    @Test
    @DisplayName("LocalDateTime への変換が正しく動作すること。")
    void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        OffsetDateTime odt = OffsetDateTime.parse("2020-01-12T12:23:34.123456+09:00");
        ZonedDateTime zdt = ZonedDateTime.of(odt.toLocalDateTime(), DateTimeUtils.JST);

        assertThat(ValueConverter.convert(null, LocalDateTime.class)).isNull();
        assertThat(ValueConverter.convert(null, LocalDateTime.class, now)).isSameAs(now);

        assertThat(ValueConverter.convert(odt.toInstant().toEpochMilli(), LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123");

        assertThat(ValueConverter.convert(Date.from(odt.toInstant()), LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123");
        assertThat(ValueConverter.convert(Timestamp.from(odt.toInstant()), LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456");
        assertThat(ValueConverter.convert(odt.toInstant(), LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456");
        assertThat(ValueConverter.convert(odt, LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456");
        assertThat(ValueConverter.convert(zdt, LocalDateTime.class))
            .hasToString("2020-01-12T12:23:34.123456");
        assertThat(ValueConverter.convert(now, LocalDateTime.class)).isSameAs(now);
    }

    @Test
    @DisplayName("LocalDate への変換が正しく動作すること。")
    void testLocalDate() {
        LocalDate now = LocalDate.now();
        OffsetDateTime odt = OffsetDateTime.parse("2020-01-12T12:23:34.123456+09:00");
        ZonedDateTime zdt = ZonedDateTime.of(odt.toLocalDateTime(), DateTimeUtils.JST);

        assertThat(ValueConverter.convert(null, LocalDate.class)).isNull();
        assertThat(ValueConverter.convert(null, LocalDate.class, now)).isSameAs(now);

        assertThat(ValueConverter.convert(odt.toInstant().toEpochMilli(), LocalDate.class))
            .hasToString("2020-01-12");

        assertThat(ValueConverter.convert(Date.from(odt.toInstant()), LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(Timestamp.from(odt.toInstant()), LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(odt.toInstant(), LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(odt, LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(zdt, LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(odt.toLocalDateTime(), LocalDate.class))
            .hasToString("2020-01-12");
        assertThat(ValueConverter.convert(now, LocalDate.class)).isSameAs(now);
    }

    @Test
    @DisplayName("String への変換が正しく動作すること。")
    void testString() {
        // null の変換。
        assertThat(ValueConverter.convert(null, String.class)).isEqualTo(null);
        assertThat(ValueConverter.convert(null, String.class, "")).isEqualTo("");

        // 数値からの変換。
        assertThat(ValueConverter.convert(123, String.class)).isEqualTo("123");
        assertThat(ValueConverter.convert(123L, String.class)).isEqualTo("123");
        assertThat(ValueConverter.convert(12345678901L, String.class)).isEqualTo("12345678901");
        assertThat(ValueConverter.convert(123.0, String.class)).isEqualTo("123.0");
        assertThat(ValueConverter.convert(123.4, String.class)).isEqualTo("123.4");
        assertThat(ValueConverter.convert(new BigDecimal("123"), String.class)).isEqualTo("123");

        // 文字列からの変換。
        assertThat(ValueConverter.convert("abc", String.class)).isEqualTo("abc");

        // 日時からの変換。
        assertThat(ValueConverter.convert(new Date(0L), String.class))
            .isEqualTo("1970-01-01T09:00+09:00");
        assertThat(ValueConverter.convert(Instant.parse("1970-01-01T00:00:00Z"), String.class))
            .isEqualTo("1970-01-01T00:00:00Z");
        assertThat(ValueConverter.convert(OffsetDateTime.parse(
                "2020-01-12T12:23:34.123+09:00"), String.class))
            .isEqualTo("2020-01-12T12:23:34.123+09:00");
    }

}
