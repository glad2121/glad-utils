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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ULIDTest {

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
    void testHuxiNextValue() {
        ULID.Generator gen = new ULID.HuxiNextValueGenerator(new TestClock(), new Random(0L));
        long timestamp = startTime().toEpochMilli();
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
    }

    @Test
    void testHuxiNextULID() {
        ULID.Generator gen = new ULID.HuxiNextULIDGenerator(new TestClock(), new Random(0L));
        long timestamp = startTime().toEpochMilli();
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
    }

    @Test
    void testSigBits() {
        ULID ulid = ULID.nextULID();
        long mostSigBits = ulid.getMostSignificantBits();
        long leastSigBits = ulid.getLeastSignificantBits();
        assertThat(new ULID(mostSigBits, leastSigBits)).isEqualTo(ulid);
    }

    @Test
    void testUUID() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        UUID uuid = ulid.toUUID();
        assertThat(ULID.from(uuid)).isEqualTo(ulid);
    }

    @Test
    void testBytes() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        byte[] data = ulid.toBytes();
        assertThat(ULID.from(data)).isEqualTo(ulid);
    }

    @Test
    void testString() {
        ULID ulid = ULID.nextULID();
        //System.out.println(ulid);
        String text = ulid.toString();
        assertThat(ULID.from(text)).isEqualTo(ulid);
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

    static Instant startTime() {
        return OffsetDateTime.parse("2020-01-23T12:23:34.123+09:00").toInstant();
    }

    static class TestClock extends Clock {

        Instant instant = startTime();

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

}
