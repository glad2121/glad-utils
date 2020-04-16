package org.glad2121.util;

import static org.assertj.core.api.Assertions.*;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;

import org.glad2121.util.ULID.Generator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ULIDTest {

    static final Instant START_TIME =
            OffsetDateTime.parse("2020-01-23T12:23:34.123+09:00").toInstant();

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testNextULID() {
        ULID.Generator gen = new ULID.DefaultGenerator(new TestClock(), new Random(0L));
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVBPHGD9PAH70YS7JVT");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVCE2ZA6BE9YS7HVW1T");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVDE2VHVX150ACFHEHY");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVEMXPNAKED5D0BBW2B");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVFR8HSSVZ99KY1X4SF");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVG1XCE24GV102YHC87");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVHPKS2T36GY5366RGF");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVJJ9R21ZV7B1F37A7P");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVKNYCB91JN702Z5VRZ");
        assertThat(ULID.nextULID(gen)).hasToString("01DZ86TBVMW2T8QXS31015S3TZ");
        //System.out.println(ULID.nextULID(gen));
    }

    @Test
    void testFromAndGetSigBits() {
        long mostSigBits = 0x0123456789ABCDEFL;
        long leastSigBits = 0x123456789ABCDEF0L;
        ULID ulid = new ULID(mostSigBits, leastSigBits);
        assertThat(ulid.getMostSignificantBits()).isEqualTo(mostSigBits);
        assertThat(ulid.getLeastSignificantBits()).isEqualTo(leastSigBits);
    }

    @Test
    void testGetAndFromSigBits() {
        ULID ulid = ULID.nextULID();
        long mostSigBits = ulid.getMostSignificantBits();
        long leastSigBits = ulid.getLeastSignificantBits();
        assertThat(new ULID(mostSigBits, leastSigBits)).isEqualTo(ulid);
    }

    @Test
    void testFromAndToUUID() {
        UUID uuid = new UUID(0x0123456789ABCDEFL, 0x123456789ABCDEF0L);
        ULID ulid = ULID.from(uuid);
        assertThat(ulid.toUUID()).isEqualTo(uuid);
    }

    @Test
    void testToAndFromUUID() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        UUID uuid = ulid.toUUID();
        assertThat(ULID.from(uuid)).isEqualTo(ulid);
    }

    @Test
    void testFromAndToString() {
        String text = "0123456789ABCDEFGHJKMNPQRS";
        ULID ulid = ULID.from(text);
        assertThat(ulid.toString()).isEqualTo(text);
    }

    @Test
    void testToAndFromString() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        String text = ulid.toString();
        assertThat(ULID.from(text)).isEqualTo(ulid);
    }

    @Test
    void testFromAndToBytes() {
        byte[] data = {
            0x01, 0x12, 0x23, 0x34,
            0x45, 0x56, 0x67, 0x78,
            (byte) 0x89, (byte) 0x9A, (byte) 0xAB, (byte) 0xBC,
            (byte) 0xCD, (byte) 0xDE, (byte) 0xEF, (byte) 0xF0
        };
        ULID ulid = ULID.from(data);
        assertThat(ulid.toBytes()).isEqualTo(data);
    }

    @Test
    void testToAndFromBytes() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        byte[] data = ulid.toBytes();
        assertThat(ULID.from(data)).isEqualTo(ulid);
    }

    @Test
    void testFromAndGetTimestampEntropy() {
        long timestamp = 0x123456789ABCL;
        byte[] entropy = {
            0x01, 0x12, 0x23, 0x34, 0x45,
            0x56, 0x67, 0x78, (byte) 0x89, (byte) 0x9A
        };
        ULID ulid = ULID.from(timestamp, entropy);
        assertThat(ulid.timestamp()).isEqualTo(timestamp);
        assertThat(ulid.entropy()).isEqualTo(entropy);
    }

    @Test
    void testGetAndFromTimestampEntropy() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        long timestamp = ulid.timestamp();
        byte[] entropy = ulid.entropy();
        assertThat(ULID.from(timestamp, entropy)).isEqualTo(ulid);
    }

    @Test
    void testComparable() {
        ULID.Generator gen = new ULID.DefaultGenerator(new TestClock(), new SecureRandom());
        ULID ulid = ULID.nextULID(gen);
        for (int i = 0; i < 10; ++i) {
            ulid = checkComparable(gen, ulid);
        }
    }

    ULID checkComparable(ULID.Generator gen, ULID prev) {
        ULID ulid = ULID.nextULID(gen);
        assertThat(ulid).isGreaterThan(prev);
        return ulid;
    }

    @Test
    void testHuxiNextValue() {
        ULID.Generator gen = new HuxiNextValueGenerator(new TestClock(), new Random(0L));
        long timestamp = START_TIME.toEpochMilli();
        Random random = new Random(0L);
        var huxi = new de.huxhorn.sulky.ulid.ULID(random);
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVBA4W3V4YBF6DKJW5Y")
            .isEqualTo(huxi.nextValue(timestamp++).toString());
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVCY0X8STBGPWEZ8983")
            .isEqualTo(huxi.nextValue(timestamp++).toString());
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVDMXPNAKED5D0BBW2B")
            .isEqualTo(huxi.nextValue(timestamp++).toString());
        //System.out.println(ULID.nextULID(gen));

        for (int i = 3; i < 10; ++i) {
            assertThat(ULID.nextULID(gen).toString())
            .isEqualTo(huxi.nextValue(timestamp++).toString());
        }
    }

    @Test
    void testHuxiNextULID() {
        ULID.Generator gen = new HuxiNextULIDGenerator(new TestClock(), new Random(0L));
        long timestamp = START_TIME.toEpochMilli();
        Random random = new Random(0L);
        var huxi = new de.huxhorn.sulky.ulid.ULID(random);
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVBBZADJM9RF6DKJW5Y")
            .isEqualTo(huxi.nextULID(timestamp++));
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVCYS7HVW1TPWEZ8983")
            .isEqualTo(huxi.nextULID(timestamp++));
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVD7Q4159VD5D0BBW2B")
            .isEqualTo(huxi.nextULID(timestamp++));
        //System.out.println(ULID.nextULID(gen));

        for (int i = 3; i < 10; ++i) {
            assertThat(ULID.nextULID(gen).toString())
            .isEqualTo(huxi.nextULID(timestamp++));
        }
    }

    @Test
    void testHuxiParseAndToString() {
        String text = "0123456789ABCDEFGHJKMNPQRS";
        var value = de.huxhorn.sulky.ulid.ULID.parseULID(text);
        assertThat(value.toString()).isEqualTo(text);
    }

    @Test
    void testAzam() {
        ULID.Generator gen = new AzamGenerator(new TestClock(), new Random(0L));
        long timestamp = START_TIME.toEpochMilli();
        Random random = new Random(0L);
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVBC2T21ESRA7CX0YPB")
            .isEqualTo(nextAzamULID(timestamp++, random));
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVCQSR316ZPS4PT0EQG")
            .isEqualTo(nextAzamULID(timestamp++, random));
        assertThat(ULID.nextULID(gen).toString())
            .isEqualTo("01DZ86TBVDPXRE13034QT10FNT")
            .isEqualTo(nextAzamULID(timestamp++, random));
        //System.out.println(ULID.nextULID(gen));

        for (int i = 3; i < 10; ++i) {
            assertThat(ULID.nextULID(gen).toString())
            .isEqualTo(nextAzamULID(timestamp++, random));
        }
    }

    String nextAzamULID(long timestamp, Random random) {
        byte[] entropy = new byte[10];
        random.nextBytes(entropy);
        return io.azam.ulidj.ULID.generate(timestamp, entropy);
    }

    @Test
    void testAzamGetAndGenerate() {
        //String text = "0123456789ABCDEFGHJKMNPQRS";
        String text = "0123456789ABCD0FGHJKMN0QRS";
        long timestamp = io.azam.ulidj.ULID.getTimestamp(text);
        byte[] entropy = io.azam.ulidj.ULID.getEntropy(text);
        assertThat(io.azam.ulidj.ULID.generate(timestamp, entropy)).isEqualTo(text);
    }

    static class TestClock extends Clock {

        Instant instant = START_TIME;

        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Instant instant() {
            Instant current = instant;
            instant = instant.plusMillis(1L);
            return current;
        }

    }

    /**
     * Huxi の {@code nextValue} と同等ロジックのジェネレータ。
     *
     * @see https://github.com/huxi/sulky/tree/master/sulky-ulid
     */
    static class HuxiNextValueGenerator extends Generator {

        public HuxiNextValueGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        @Override
        public ULID nextULID() {
            long mostSigBits = (timestamp() << 16) | (random.nextLong() & 0xFFFF);
            long leastSigBits = random.nextLong();
            return new ULID(mostSigBits, leastSigBits);
        }

    }

    /**
     * Huxi の {@code nextULID} と同等ロジックのジェネレータ。
     *
     * @see https://github.com/huxi/sulky/tree/master/sulky-ulid
     */
    static class HuxiNextULIDGenerator extends Generator {

        public HuxiNextULIDGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        @Override
        public ULID nextULID() {
            long timestamp = timestamp();
            long part1 = random.nextLong();
            long part2 = random.nextLong();
            long mostSigBits = (timestamp << 16) | ((part1 >>> 24) & 0xFFFFL);
            long leastSigBits = (part1 << 40) | (part2 & 0xFF_FFFF_FFFFL);
            return new ULID(mostSigBits, leastSigBits);
        }

    }

    /**
     * Azam の ulidj と同等ロジックのジェネレータ。
     *
     * @see https://github.com/azam/ulidj
     */
    static class AzamGenerator extends Generator {

        public AzamGenerator(Clock clock, Random random) {
            super(clock, random);
        }

        @Override
        public ULID nextULID() {
            long timestamp = timestamp();
            byte[] entropy = new byte[10];
            random.nextBytes(entropy);
            // おそらくバグ。
            entropy[2] = (byte) (entropy[2] & 0xF0);
            entropy[7] = (byte) (entropy[7] & 0xF0);
            return ULID.from(timestamp, entropy);
        }

    }

}
