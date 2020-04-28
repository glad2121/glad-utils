package org.glad2121.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link NumberUtils} の単体テスト。
 */
class NumberUtilsTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("int から12進数へ正しく変換できること。")
    void testIntToString12() {
        assertThat(NumberUtils.toString(0, 12)).isEqualTo("0");
        assertThat(NumberUtils.toString(11, 12)).isEqualTo("B");
        assertThat(NumberUtils.toString(12, 12)).isEqualTo("10");
        assertThat(NumberUtils.toString(65534, 12)).isEqualTo("31B12");
        assertThat(NumberUtils.toString(Integer.MAX_VALUE, 12)).isEqualTo("4BB2308A7");
        assertThat(NumberUtils.toString(-1, 12)).isEqualTo("-1");
        assertThat(NumberUtils.toString(Integer.MIN_VALUE, 12)).isEqualTo("-4BB2308A8");
    }

    @Test
    @DisplayName("int からの変換で基数が範囲外の場合、例外がスローされること。")
    void testIntToStringError() {
        assertThatThrownBy(() -> NumberUtils.toString(0, 1))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.toString(0, 33))
            .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("long から12進数へ正しく変換できること。")
    void testLongToString12() {
        assertThat(NumberUtils.toString(0L, 12)).isEqualTo("0");
        assertThat(NumberUtils.toString(11L, 12)).isEqualTo("B");
        assertThat(NumberUtils.toString(12L, 12)).isEqualTo("10");
        assertThat(NumberUtils.toString(65534L, 12)).isEqualTo("31B12");
        assertThat(NumberUtils.toString(Long.MAX_VALUE, 12)).isEqualTo("41A792678515120367");
        assertThat(NumberUtils.toString(-1L, 12)).isEqualTo("-1");
        assertThat(NumberUtils.toString(Long.MIN_VALUE, 12)).isEqualTo("-41A792678515120368");
    }

    @Test
    @DisplayName("long からの変換で基数が範囲外の場合、例外がスローされること。")
    void testLongToStringError() {
        assertThatThrownBy(() -> NumberUtils.toString(0L, 1))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.toString(0L, 33))
            .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("int から16進数へ正しく変換できること。")
    void testIntToHexString() {
        assertThat(NumberUtils.toHexString(0, 0)).isEqualTo("0");
        assertThat(NumberUtils.toHexString(0, 4)).isEqualTo("0000");
        assertThat(NumberUtils.toHexString(15, 0)).isEqualTo("F");
        assertThat(NumberUtils.toHexString(15, 4)).isEqualTo("000F");
        assertThat(NumberUtils.toHexString(16, 0)).isEqualTo("10");
        assertThat(NumberUtils.toHexString(16, 4)).isEqualTo("0010");
        assertThat(NumberUtils.toHexString(65534, 2)).isEqualTo("FFFE");
        assertThat(NumberUtils.toHexString(65534, 8)).isEqualTo("0000FFFE");
        assertThat(NumberUtils.toHexString(1048574, 2)).isEqualTo("FFFFE");
        assertThat(NumberUtils.toHexString(1048574, 8)).isEqualTo("000FFFFE");
        assertThat(NumberUtils.toHexString(1048578, 2)).isEqualTo("100002");
        assertThat(NumberUtils.toHexString(1048578, 8)).isEqualTo("00100002");
        assertThat(NumberUtils.toHexString(Integer.MAX_VALUE, 0)).isEqualTo("7FFFFFFF");
        assertThat(NumberUtils.toHexString(-1, 0)).isEqualTo("FFFFFFFF");
        assertThat(NumberUtils.toHexString(Integer.MIN_VALUE, 0)).isEqualTo("80000000");
    }

    @Test
    @DisplayName("long から16進数へ正しく変換できること。")
    void testLongToHexString() {
        assertThat(NumberUtils.toHexString(0L, 0)).isEqualTo("0");
        assertThat(NumberUtils.toHexString(0L, 4)).isEqualTo("0000");
        assertThat(NumberUtils.toHexString(15L, 0)).isEqualTo("F");
        assertThat(NumberUtils.toHexString(15L, 4)).isEqualTo("000F");
        assertThat(NumberUtils.toHexString(16L, 0)).isEqualTo("10");
        assertThat(NumberUtils.toHexString(16L, 4)).isEqualTo("0010");
        assertThat(NumberUtils.toHexString(65534L, 2)).isEqualTo("FFFE");
        assertThat(NumberUtils.toHexString(65534L, 8)).isEqualTo("0000FFFE");
        assertThat(NumberUtils.toHexString(1048574L, 2)).isEqualTo("FFFFE");
        assertThat(NumberUtils.toHexString(1048574L, 8)).isEqualTo("000FFFFE");
        assertThat(NumberUtils.toHexString(1048578L, 2)).isEqualTo("100002");
        assertThat(NumberUtils.toHexString(1048578L, 8)).isEqualTo("00100002");
        assertThat(NumberUtils.toHexString(Long.MAX_VALUE, 0)).isEqualTo("7FFFFFFFFFFFFFFF");
        assertThat(NumberUtils.toHexString(-1L, 0)).isEqualTo("FFFFFFFFFFFFFFFF");
        assertThat(NumberUtils.toHexString(Long.MIN_VALUE, 0)).isEqualTo("8000000000000000");
    }

    @Test
    @DisplayName("int から32進数へ正しく変換できること。")
    void testIntToCrockford32() {
        assertThat(NumberUtils.toCrockford32(0, 0)).isEqualTo("0");
        assertThat(NumberUtils.toCrockford32(0, 4)).isEqualTo("0000");
        assertThat(NumberUtils.toCrockford32(31, 0)).isEqualTo("Z");
        assertThat(NumberUtils.toCrockford32(31, 4)).isEqualTo("000Z");
        assertThat(NumberUtils.toCrockford32(32, 0)).isEqualTo("10");
        assertThat(NumberUtils.toCrockford32(32, 4)).isEqualTo("0010");
        assertThat(NumberUtils.toCrockford32(65534, 2)).isEqualTo("1ZZY");
        assertThat(NumberUtils.toCrockford32(65534, 8)).isEqualTo("00001ZZY");
        assertThat(NumberUtils.toCrockford32(1048574, 2)).isEqualTo("ZZZY");
        assertThat(NumberUtils.toCrockford32(1048574, 8)).isEqualTo("0000ZZZY");
        assertThat(NumberUtils.toCrockford32(1048578, 2)).isEqualTo("10002");
        assertThat(NumberUtils.toCrockford32(1048578, 8)).isEqualTo("00010002");
        assertThat(NumberUtils.toCrockford32(Integer.MAX_VALUE, 0)).isEqualTo("1ZZZZZZ");
        assertThat(NumberUtils.toCrockford32(-1, 0)).isEqualTo("3ZZZZZZ");
        assertThat(NumberUtils.toCrockford32(Integer.MIN_VALUE, 0)).isEqualTo("2000000");
    }

    @Test
    @DisplayName("long から32進数へ正しく変換できること。")
    void testLongToCrockford32() {
        assertThat(NumberUtils.toCrockford32(0L, 0)).isEqualTo("0");
        assertThat(NumberUtils.toCrockford32(0L, 4)).isEqualTo("0000");
        assertThat(NumberUtils.toCrockford32(31L, 0)).isEqualTo("Z");
        assertThat(NumberUtils.toCrockford32(31L, 4)).isEqualTo("000Z");
        assertThat(NumberUtils.toCrockford32(32L, 0)).isEqualTo("10");
        assertThat(NumberUtils.toCrockford32(32L, 4)).isEqualTo("0010");
        assertThat(NumberUtils.toCrockford32(65534L, 2)).isEqualTo("1ZZY");
        assertThat(NumberUtils.toCrockford32(65534L, 8)).isEqualTo("00001ZZY");
        assertThat(NumberUtils.toCrockford32(1048574L, 2)).isEqualTo("ZZZY");
        assertThat(NumberUtils.toCrockford32(1048574L, 8)).isEqualTo("0000ZZZY");
        assertThat(NumberUtils.toCrockford32(1048578L, 2)).isEqualTo("10002");
        assertThat(NumberUtils.toCrockford32(1048578L, 8)).isEqualTo("00010002");
        assertThat(NumberUtils.toCrockford32(Long.MAX_VALUE, 0)).isEqualTo("7ZZZZZZZZZZZZ");
        assertThat(NumberUtils.toCrockford32(-1L, 0)).isEqualTo("FZZZZZZZZZZZZ");
        assertThat(NumberUtils.toCrockford32(Long.MIN_VALUE, 0)).isEqualTo("8000000000000");
    }

    @Test
    @DisplayName("16進数から int へ正しく解析できること。")
    void testParseInt16() {
        assertThat(NumberUtils.parseInt("0", 16)).isEqualTo(0);
        assertThat(NumberUtils.parseInt("1", 16)).isEqualTo(1);
        assertThat(NumberUtils.parseInt("0_0000_0001", 16)).isEqualTo(1);
        assertThat(NumberUtils.parseInt("7FFF_FFFF", 16)).isEqualTo(Integer.MAX_VALUE);
        assertThat(NumberUtils.parseInt("FFFF_FFFF", 16)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-1", 16)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-0_0000_0001", 16)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-8000_0000", 16)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("32進数から int へ正しく解析できること。")
    void testParseInt32() {
        assertThat(NumberUtils.parseInt("0", 32)).isEqualTo(0);
        assertThat(NumberUtils.parseInt("1", 32)).isEqualTo(1);
        assertThat(NumberUtils.parseInt("0000_0001", 32)).isEqualTo(1);
        assertThat(NumberUtils.parseInt("1ZZ_ZZZZ", 32)).isEqualTo(Integer.MAX_VALUE);
        assertThat(NumberUtils.parseInt("3ZZ_ZZZZ", 32)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-1", 32)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-0000_0001", 32)).isEqualTo(-1);
        assertThat(NumberUtils.parseInt("-200_0000", 32)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("int への解析でエラーが発生する場合。")
    void testParseIntError() {
        assertThatThrownBy(() -> NumberUtils.parseInt(null, 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseInt("", 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseInt("-", 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseInt("0", 1))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseInt("0", 33))
            .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("16進数から long へ正しく解析できること。")
    void testParseLong16() {
        assertThat(NumberUtils.parseLong("0", 16)).isEqualTo(0L);
        assertThat(NumberUtils.parseLong("1", 16)).isEqualTo(1L);
        assertThat(NumberUtils.parseLong("0_0000_0000_0000_0001", 16)).isEqualTo(1L);
        assertThat(NumberUtils.parseLong("7FFF_FFFF_FFFF_FFFF", 16)).isEqualTo(Long.MAX_VALUE);
        assertThat(NumberUtils.parseLong("FFFF_FFFF_FFFF_FFFF", 16)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-1", 16)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-0_0000_0000_0000_0001", 16)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-8000_0000_0000_0000", 16)).isEqualTo(Long.MIN_VALUE);
    }

    @Test
    @DisplayName("32進数から long へ正しく解析できること。")
    void testParseLong32() {
        assertThat(NumberUtils.parseLong("0", 32)).isEqualTo(0L);
        assertThat(NumberUtils.parseLong("1", 32)).isEqualTo(1L);
        assertThat(NumberUtils.parseLong("00_0000_0000_0001", 32)).isEqualTo(1L);
        assertThat(NumberUtils.parseLong("7_ZZZZ_ZZZZ_ZZZZ", 32)).isEqualTo(Long.MAX_VALUE);
        assertThat(NumberUtils.parseLong("F_ZZZZ_ZZZZ_ZZZZ", 32)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-1", 32)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-00_0000_0000_0001", 32)).isEqualTo(-1L);
        assertThat(NumberUtils.parseLong("-8_0000_0000_0000", 32)).isEqualTo(Long.MIN_VALUE);
    }

    @Test
    @DisplayName("long への解析でエラーが発生する場合。")
    void testParseLongError() {
        assertThatThrownBy(() -> NumberUtils.parseLong(null, 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseLong("", 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseLong("-", 10))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseLong("0", 1))
            .isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> NumberUtils.parseLong("0", 33))
            .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("文字から数字へ正しく変換できること。")
    void testCharToNum() {
        assertThat(NumberUtils.charToNum('0')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('9')).isEqualTo(9);
        assertThat(NumberUtils.charToNum('A')).isEqualTo(10);
        assertThat(NumberUtils.charToNum('I')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('L')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('O')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('U')).isEqualTo(-1);
        assertThat(NumberUtils.charToNum('Z')).isEqualTo(31);
        assertThat(NumberUtils.charToNum('a')).isEqualTo(10);
        assertThat(NumberUtils.charToNum('i')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('l')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('o')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('u')).isEqualTo(-1);
        assertThat(NumberUtils.charToNum('z')).isEqualTo(31);
        assertThat(NumberUtils.charToNum('０')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('９')).isEqualTo(9);
        assertThat(NumberUtils.charToNum('Ａ')).isEqualTo(10);
        assertThat(NumberUtils.charToNum('Ｉ')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('Ｌ')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('Ｏ')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('Ｕ')).isEqualTo(-1);
        assertThat(NumberUtils.charToNum('Ｚ')).isEqualTo(31);
        assertThat(NumberUtils.charToNum('ａ')).isEqualTo(10);
        assertThat(NumberUtils.charToNum('ｉ')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('ｌ')).isEqualTo(1);
        assertThat(NumberUtils.charToNum('ｏ')).isEqualTo(0);
        assertThat(NumberUtils.charToNum('ｕ')).isEqualTo(-1);
        assertThat(NumberUtils.charToNum('ｚ')).isEqualTo(31);
    }

}
