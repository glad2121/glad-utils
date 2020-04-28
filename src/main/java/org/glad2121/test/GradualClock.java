package org.glad2121.test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 時点を取得するたびに指定されたミリ秒ずつ進むクロック。
 *
 * @author glad2121
 */
public class GradualClock extends Clock {

    /**
     * 時点を保持するリファレンス。
     */
    private final AtomicReference<Instant> instant;

    /**
     * 加算するミリ秒数。
     */
    private final long millisToAdd;

    /**
     * タイムゾーン。
     */
    private final ZoneId zone;

    /**
     * コンストラクタ。
     *
     * @param start 開始する時点
     * @param millisToAdd 加算するミリ秒数
     * @param zone タイムゾーン
     */
    public GradualClock(Instant start, long millisToAdd, ZoneId zone) {
        this(new AtomicReference<>(start), millisToAdd, zone);
    }

    /**
     * コンストラクタ。
     *
     * @param start 開始するローカル日時を表す文字列
     * @param millisToAdd 加算するミリ秒数
     * @param zone タイムゾーン
     */
    public GradualClock(String start, long millisToAdd, ZoneId zone) {
        this(parseInstant(start, zone), millisToAdd, zone);
    }

    /**
     * 内部的に使用するコンストラクタ。
     *
     * @param instant 時点を保持するリファレンス
     * @param millisToAdd 加算するミリ秒数
     * @param zone タイムゾーン
     */
    GradualClock(AtomicReference<Instant> instant, long millisToAdd, ZoneId zone) {
        this.instant = instant;
        this.millisToAdd = millisToAdd;
        this.zone = zone;
    }

    /**
     * ローカル日時を表す文字列を解析し、時点を返します。
     *
     * @param localDateTime ローカル日時を表す文字列
     * @param zone タイムゾーン
     * @return 時点
     */
    static Instant parseInstant(String localDateTime, ZoneId zone) {
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime);
        return ZonedDateTime.of(dateTime, zone).toInstant();
    }

    /**
     * タイムゾーンを返します。
     */
    @Override
    public ZoneId getZone() {
        return zone;
    }

    /**
     * 指定されたタイムゾーンを使って、このクロックのコピーを返します。
     */
    @Override
    public Clock withZone(ZoneId zone) {
        return new GradualClock(instant, millisToAdd, zone);
    }

    /**
     * このクロックの現在の時点を返します。
     */
    @Override
    public Instant instant() {
        return instant.getAndUpdate(x -> x.plusMillis(millisToAdd));
    }

}
