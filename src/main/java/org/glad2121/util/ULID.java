package org.glad2121.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 不変の ULID (Universally Unique Lexicographically Sortable Identifier) を表すクラス。
 * <p>
 * タイムスタンプとランダム値を元に作成した ID であり、
 * タイムスタンプ順にソート可能で、一定の推測困難性を併せ持つ。
 *
 * @author glad2121
 * @see https://github.com/ulid/spec
 */
public final class ULID implements Serializable, Comparable<ULID> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 最上位ビット列
     */
    private final long mostSigBits;

    /**
     * 最下位ビット列
     */
    private final long leastSigBits;

    /**
     * コンストラクタ。
     *
     * @param mostSigBits 最上位ビット列
     * @param leastSigBits 最下位ビット列
     */
    public ULID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * デフォルトのジェネレータで、次の ULID を生成します。
     *
     * @return ULID
     */
    public static ULID nextULID() {
        return nextULID(DefaultGenerator.INSTANCE);
    }

    /**
     * 指定されたジェネレータで、次の ULID を生成します。
     *
     * @param generator ジェネレータ
     * @return ULID
     */
    public static ULID nextULID(Generator generator) {
        return generator.nextULID();
    }

    /**
     * UUID のビット列から ULID へ変換します。
     *
     * @param uuid UUID
     * @return ULID
     */
    public static ULID from(UUID uuid) {
        return new ULID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    /**
     * 指定されたビット列から ULID へ変換します。
     *
     * @param data ビット列
     * @return ULID
     */
    public static ULID from(byte[] data) {
        Objects.requireNonNull(data, "data must not be null");
        if (data.length != 16) {
            throw new IllegalArgumentException("data must be 16 bytes");
        }
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0; i < 8; ++i) {
            mostSigBits = (mostSigBits << 8) | (data[i] & 0xFF);
        }
        for (int i = 8; i < 16; ++i) {
            leastSigBits = (leastSigBits << 8) | (data[i] & 0xFF);
        }
        return new ULID(mostSigBits, leastSigBits);
    }

    /**
     * 指定された ULID 表現の文字列から ULID へ変換します。
     *
     * @param ulid ULID 表現の文字列
     * @return ULID
     */
    public static ULID from(String ulid) {
        Objects.requireNonNull(ulid, "ulid must not be null");
        if (ulid.length() != 26) {
            throw new IllegalArgumentException("ulid must be 26 chars");
        }
        long timestamp = NumberUtils.parseLong(ulid.substring(0, 10), 32);
        long part1 = NumberUtils.parseLong(ulid.substring(10, 18), 32);
        long part2 = NumberUtils.parseLong(ulid.substring(18), 32);
        long mostSigBits = (timestamp << 16) | (part1 >>> 24);
        long leastSigBits = (part1 << 40) | part2;
        return new ULID(mostSigBits, leastSigBits);
    }

    /**
     * 最上位ビット列を返します。
     *
     * @return 最上位ビット列
     */
    public long getLeastSignificantBits() {
        return leastSigBits;
    }

    /**
     * 最下位ビット列を返します。
     *
     * @return 最下位ビット列
     */
    public long getMostSignificantBits() {
        return mostSigBits;
    }

    /**
     * この ULID に関連したタイムスタンプを返します。
     *
     * @return タイムスタンプ
     */
    public long timestamp() {
        return mostSigBits >>> 16;
    }

    /**
     * この ULID のビット列から UUID へ変換します。
     *
     * @return UUID
     */
    public UUID toUUID() {
        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * この ULID のビット列を返します。
     *
     * @return ビット列
     */
    public byte[] toBytes() {
        byte[] result = new byte[16];
        long msb = mostSigBits;
        for (int i = 7; i >= 0; --i) {
            result[i] = (byte) (msb & 0xFF);
            msb >>= 8;
        }
        long lsb = leastSigBits;
        for (int i = 15; i >= 8; --i) {
            result[i] = (byte) (lsb & 0xFF);
            lsb >>= 8;
        }
        return result;
    }

    /**
     * この ULID の文字列表現を返します。
     */
    @Override
    public String toString() {
        char[] buf = new char[26];
        NumberUtils.toCrockford32(timestamp(), buf, 0, 10);
        long value = ((mostSigBits & 0xFFFFL) << 24) | (leastSigBits >>> 40);
        NumberUtils.toCrockford32(value, buf, 10, 8);
        NumberUtils.toCrockford32(leastSigBits, buf, 18, 8);
        return new String(buf);
    }

    /**
     * この ULID のハッシュ値を返します。
     */
    @Override
    public int hashCode() {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int) (hilo >> 32)) ^ (int) hilo;
    }

    /**
     * この ULID を他のオブジェクトと等しいか判定します。
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != ULID.class) {
            return false;
        }
        ULID other = (ULID) obj;
        return (mostSigBits == other.mostSigBits &&
                leastSigBits == other.leastSigBits);
    }

    /**
     * この ULID を他の ULID と比較します。
     */
    @Override
    public int compareTo(ULID other) {
        return (this.mostSigBits < other.mostSigBits) ? -1 :
                (this.mostSigBits > other.mostSigBits) ? 1 :
                (this.leastSigBits < other.leastSigBits) ? -1 :
                (this.leastSigBits > other.leastSigBits) ? 1 : 0;
    }

    /**
     * ULID のジェネレータ。
     *
     * @author glad2121
     */
    public static abstract class Generator {

        /**
         * タイムスタンプが上限を超えたか判定するためのマスク。
         */
        static final long TIMESTAMP_OVERFLOW_MASK = 0xFFFF_0000_0000_0000L;

        /**
         * タイムスタンプを取得する {@code Clock}。
         */
        protected final Clock clock;

        /**
         * ランダム値を生成する {@code Random} オブジェクト。
         */
        protected final Random random;

        /**
         * システムクロックと {@code SecureRandom} を用いたコンストラクタ。
         */
        public Generator() {
            this(Clock.systemUTC(), new SecureRandom());
        }

        /**
         * 指定された {@code Clock} と {@code Random} を用いたコンストラクタ。
         *
         * @param clock {@code Clock}
         * @param random {@code Random}
         */
        public Generator(Clock clock, Random random) {
            this.clock = clock;
            this.random = random;
        }

        /**
         * 次の ULID を生成します。
         * <p>
         * サブクラスでオーバーライドする。
         *
         * @return ULID
         */
        public abstract ULID nextULID();

        /**
         * タイムスタンプが範囲内かチェックします。
         *
         * @param timestamp タイムスタンプ
         */
        protected void checkTimestamp(long timestamp) {
            if ((timestamp & TIMESTAMP_OVERFLOW_MASK) != 0) {
                throw new IllegalArgumentException("Unsupport timestamp: " + timestamp);
            }
        }

    }

    /**
     * デフォルトの ULID ジェネレータ。
     *
     * @author glad2121
     */
    public static class DefaultGenerator extends Generator {

        /**
         * このジェネレータのインスタンス。
         */
        static final DefaultGenerator INSTANCE = new DefaultGenerator();

        /**
         * システムクロックと {@code SecureRandom} を用いたコンストラクタ。
         */
        public DefaultGenerator() {
        }

        /**
         * 指定された {@code Clock} と {@code Random} を用いたコンストラクタ。
         *
         * @param clock {@code Clock}
         * @param random {@code Random}
         */
        public DefaultGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        /**
         * 次の ULID を生成します。
         */
        @Override
        public ULID nextULID() {
            long timestamp = clock.millis();
            checkTimestamp(timestamp);
            long mostSigBits = (timestamp << 16) | (random.nextInt() & 0xFFFF);
            long leastSigBits = random.nextLong();
            return new ULID(mostSigBits, leastSigBits);
        }

    }

    /**
     * Huxi の {@code nextValue} と同等ロジックのジェネレータ。
     *
     * @author glad2121
     * @see https://github.com/huxi/sulky/tree/master/sulky-ulid
     */
    static class HuxiNextValueGenerator extends Generator {

        /**
         * システムクロックと {@code SecureRandom} を用いたコンストラクタ。
         */
        public HuxiNextValueGenerator() {
        }

        /**
         * 指定された {@code Clock} と {@code Random} を用いたコンストラクタ。
         *
         * @param clock {@code Clock}
         * @param random {@code Random}
         */
        public HuxiNextValueGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        /**
         * 次の ULID を生成します。
         */
        @Override
        public ULID nextULID() {
            long timestamp = clock.millis();
            checkTimestamp(timestamp);
            long mostSigBits = (timestamp << 16) | (random.nextLong() & 0xFFFF);
            long leastSigBits = random.nextLong();
            return new ULID(mostSigBits, leastSigBits);
        }

    }

    /**
     * Huxi の {@code nextULID} と同等ロジックのジェネレータ。
     *
     * @author glad2121
     * @see https://github.com/huxi/sulky/tree/master/sulky-ulid
     */
    static class HuxiNextULIDGenerator extends Generator {

        /**
         * システムクロックと {@code SecureRandom} を用いたコンストラクタ。
         */
        public HuxiNextULIDGenerator() {
        }

        /**
         * 指定された {@code Clock} と {@code Random} を用いたコンストラクタ。
         *
         * @param clock {@code Clock}
         * @param random {@code Random}
         */
        public HuxiNextULIDGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        /**
         * 次の ULID を生成します。
         */
        @Override
        public ULID nextULID() {
            long timestamp = clock.millis();
            checkTimestamp(timestamp);
            long part1 = random.nextLong();
            long part2 = random.nextLong();
            long mostSigBits = (timestamp << 16) | ((part1 >>> 24) & 0xFFFFL);
            long leastSigBits = (part1 << 40) | (part2 & 0xFF_FFFF_FFFFL);
            return new ULID(mostSigBits, leastSigBits);
        }

    }

}
