package org.glad2121.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 現在の時点を提供するクロックを保持するクラス。
 *
 * @author glad2121
 */
public final class ClockHolder {

    /**
     * デフォルトのクロック。
     */
    static final Clock DEFAULT = Clock.systemDefaultZone();

    /**
     * クロックのインスタンス。
     */
    private static final AtomicReference<Clock> instance =
            new AtomicReference<>(DEFAULT);

    /**
     * 使用しないコンストラクタ。
     */
    private ClockHolder() {
    }

    /**
     * クロックを返します。
     *
     * @return クロック
     */
    public static Clock get() {
        return instance.get();
    }

    /**
     * クロックを設定し、以前の値を返します。
     *
     * @param clock クロック
     */
    public static Clock setClock(Clock clock) {
        return instance.getAndSet(clock != null ? clock : DEFAULT);
    }

    /**
     * デフォルトのクロックに戻します。
     */
    public static void reset() {
        setClock(DEFAULT);
    }

    /**
     * 常に同じ時点を返す固定値のクロックを返します。
     *
     * @param localDateTime ローカル日時を表す文字列
     * @param zone タイムゾーン
     * @return 固定値のクロック
     */
    public static Clock fixedClock(String localDateTime, ZoneId zone) {
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime);
        return Clock.fixed(ZonedDateTime.of(dateTime, zone).toInstant(), zone);
    }

}
