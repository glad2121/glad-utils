package org.glad2121.test;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link GradualClock} の単体テスト。
 */
class GradualClockTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("GradualClock の動作確認。")
    void testAll() {
        ZoneId jst = ZoneId.of("Asia/Tokyo");
        Clock clock = new GradualClock("2020-01-23T12:23:34.123", 10L, jst);
        assertThat(clock.getZone()).isEqualTo(jst);
        // 10ミリ秒ずつ進む。
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.123Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.133Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.143Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.153Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.163Z");

        // 別のタイムゾーンのクロックを生成。
        ZoneId utc = ZoneOffset.UTC;
        Clock utcClock = clock.withZone(utc);
        assertThat(utcClock.getZone()).isEqualTo(utc);
        assertThat(utcClock.instant()).hasToString("2020-01-23T03:23:34.173Z");
        assertThat(utcClock.instant()).hasToString("2020-01-23T03:23:34.183Z");
        assertThat(utcClock.instant()).hasToString("2020-01-23T03:23:34.193Z");

        // 両方のクロックで時点は共有される。
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.203Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.213Z");
        assertThat(clock.instant()).hasToString("2020-01-23T03:23:34.223Z");
    }

}
