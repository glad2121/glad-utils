package org.glad2121.util;

import java.nio.charset.StandardCharsets;

/**
 * 数値関連のユーティリティ。
 *
 * @author glad2121
 */
public class NumberUtils {

    /**
     * 数値から文字への変換表。
     * <p>
     * I, L, O, U は 1, 1, 0, V と紛らわしいので使用しない。
     */
    private static final byte[] NUM_TO_DIGIT =
            "0123456789ABCDEFGHJKMNPQRSTVWXYZ".getBytes(StandardCharsets.US_ASCII);

    /**
     * 英字から数値への変換表。
     * <p>
     * I, L, O は 1, 1, 0 へ変換、U はエラーとする。
     */
    private static final int[] ALPHA_TO_NUM = {
        10, 11, 12, 13, 14, 15, 16, 17, 1, 18, 19, 1, 20, 21,
        0, 22, 23, 24, 25, 26, -1, 27, 28, 29, 30, 31
    };

    /**
     * 最小の基数。
     */
    static final int MIN_RADIX = 2;

    /**
     * 最大の基数。
     */
    static final int MAX_RADIX = NUM_TO_DIGIT.length;

    /**
     * 使用しないコンストラクタ。
     */
    protected NumberUtils() {
    }

    /**
     * {@code int} を基数 {@code radix} の文字列表現に変換します。
     *
     * @param value {@code int}
     * @param radix 基数
     * @return 基数 {@code radix} の文字列表現
     */
    public static String toString(int value, int radix) {
        checkRadix(radix);
        if (radix == 10) {
            return String.valueOf(value);
        }
        byte[] buf = new byte[33];
        boolean negative = (value < 0);
        if (!negative) {
            value = -value;
        }
        int pos = buf.length;
        do {
            buf[--pos] = NUM_TO_DIGIT[-(value % radix)];
            value /= radix;
        } while (value < 0);
        if (negative) {
            buf[--pos] = '-';
        }
        return new String(buf, pos, buf.length - pos, StandardCharsets.US_ASCII);
    }

    /**
     * {@code long} を基数 {@code radix} の文字列表現に変換します。
     *
     * @param value {@code long}
     * @param radix 基数
     * @return 基数 {@code radix} の文字列表現
     */
    public static String toString(long value, int radix) {
        checkRadix(radix);
        if (radix == 10) {
            return String.valueOf(value);
        }
        byte[] buf = new byte[65];
        boolean negative = (value < 0);
        if (!negative) {
            value = -value;
        }
        int pos = buf.length;
        do {
            buf[--pos] = NUM_TO_DIGIT[- (int) (value % radix)];
            value /= radix;
        } while (value < 0);
        if (negative) {
            buf[--pos] = '-';
        }
        return new String(buf, pos, buf.length - pos, StandardCharsets.US_ASCII);
    }

    /**
     * {@code int} を16進数文字列に変換します。
     *
     * @param value {@code int}
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return 16進数文字列
     */
    public static String toHexString(int value, int length) {
        return toUnsignedString(value, 4, length);
    }

    /**
     * {@code long} を16進数文字列に変換します。
     *
     * @param value {@code long}
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return 16進数文字列
     */
    public static String toHexString(long value, int length) {
        return toUnsignedString(value, 4, length);
    }

    /**
     * {@code int} を Crockford's Base32 に変換します。
     *
     * @param value {@code int}
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return Crockford's Base32
     */
    public static String toCrockford32(int value, int length) {
        return toUnsignedString(value, 5, length);
    }

    /**
     * {@code long} を Crockford's Base32 に変換します。
     *
     * @param value {@code long}
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return Crockford's Base32
     */
    public static String toCrockford32(long value, int length) {
        return toUnsignedString(value, 5, length);
    }

    /**
     * {@code long} を Crockford's Base32 に変換します。
     *
     * @param value {@code long}
     * @param buf 結果を格納するバッファ
     * @param offset 結果を格納するオフセット
     * @param count 結果を格納する文字数
     */
    static void toCrockford32(long value, char[] buf, int offset, int count) {
        toUnsignedString(value, 5, buf, offset, count);
    }

    /**
     * {@code int} を {@code 2 ^ shift} 進数文字列に変換します。
     *
     * @param value {@code int}
     * @param shift シフト数
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return {@code 2 ^ shift} 進数文字列
     */
    static String toUnsignedString(int value, int shift, int length) {
        byte[] buf = new byte[32];
        final int mask = (1 << shift) - 1;
        int pos = buf.length;
        do {
            buf[--pos] = NUM_TO_DIGIT[value & mask];
            value >>>= shift;
            --length;
        } while (value != 0 || length > 0);
        return new String(buf, pos, buf.length - pos, StandardCharsets.US_ASCII);
    }

    /**
     * {@code long} を {@code 2 ^ shift} 進数文字列に変換します。
     *
     * @param value {@code long}
     * @param shift シフト数
     * @param length 最小桁数（足りない場合は結果を左から0埋め）
     * @return {@code 2 ^ shift} 進数文字列
     */
    static String toUnsignedString(long value, int shift, int length) {
        byte[] buf = new byte[64];
        final int mask = (1 << shift) - 1;
        int pos = buf.length;
        do {
            buf[--pos] = NUM_TO_DIGIT[(int) value & mask];
            value >>>= shift;
            --length;
        } while (value != 0 || length > 0);
        return new String(buf, pos, buf.length - pos, StandardCharsets.US_ASCII);
    }

    /**
     * {@code long} を {@code 2 ^ shift} 進数文字列に変換します。
     *
     * @param value {@code long}
     * @param shift シフト数
     * @param buf 結果を格納するバッファ
     * @param offset 結果を格納するオフセット
     * @param count 結果を格納する文字数
     */
    static void toUnsignedString(long value, int shift, char[] buf, int offset, int count) {
        final int mask = (1 << shift) - 1;
        for (int i = offset + count - 1; offset <= i; --i) {
            buf[i] = (char) NUM_TO_DIGIT[(int) value & mask];
            value >>>= shift;
        }
    }

    /**
     * 文字列を基数 {@code radix} で {@code int} に変換します。
     *
     * @param s 文字列
     * @param radix 基数
     * @return {@code int}
     */
    public static int parseInt(CharSequence s, int radix) {
        long value = parseLong(s, radix);
        if (Integer.MIN_VALUE <= value && value <= 0xFFFF_FFFFL) {
            return (int) value;
        }
        throw new NumberFormatException("\"" + s + "\" exceeds range of int");
    }

    /**
     * 文字列を基数 {@code radix} で {@code long} に変換します。
     *
     * @param s 文字列
     * @param radix 基数
     * @return {@code long}
     */
    public static long parseLong(CharSequence s, int radix) {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        checkRadix(radix);
        int len = s.length();
        if (len == 0) {
            throw makeNumberFormatException(s, 0);
        }
        boolean negative = false;
        int pos = 0;
        char first = s.charAt(0);
        if (first == '-') {
            negative = true;
            ++pos;
        } else if (first == '+') {
            ++pos;
        }
        if (pos == len) {
            throw makeNumberFormatException(s, pos);
        }
        long result = 0L;
        while (pos < len) {
            char c = s.charAt(pos);
            if (c != '_') {
                int num = charToNum(c);
                if (num < 0 || radix <= num) {
                    throw makeNumberFormatException(s, pos);
                }
                result = (result * radix) + num;
            }
            ++pos;
        }
        return negative ? -result : result;
    }

    /**
     * 基数が適切かチェックします。
     *
     * @param radix 基数
     */
    static void checkRadix(int radix) {
        if (radix < MIN_RADIX) {
            throw new NumberFormatException(
                    "radix " + radix + " less than " + MIN_RADIX);
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException(
                    "radix " + radix + " greater than " + MAX_RADIX);
        }
    }

    /**
     * 文字列とエラー発生位置から {@code NumberFormatException} を生成します。
     *
     * @param s 文字列
     * @param index 位置
     * @return {@code NumberFormatException}
     */
    static NumberFormatException makeNumberFormatException(CharSequence s, int index) {
        return new NumberFormatException("error at:" + index + " in: \"" + s + "\"");
    }

    /**
     * 文字を数値に変換します。
     *
     * @param c 文字
     * @return 数値
     */
    static int charToNum(char c) {
        if (Character.isDigit(c)) {
            return c & 0xF;
        }
        if (Character.isAlphabetic(c)) {
            return ALPHA_TO_NUM[(c & 0x1F) - 1];
        }
        return -1;
    }

}
