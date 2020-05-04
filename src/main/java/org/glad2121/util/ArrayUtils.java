package org.glad2121.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 配列関連のユーティリティ。
 *
 * @author glad2121
 */
public final class ArrayUtils {

    /**
     * 使用しないコンストラクタ。
     */
    private ArrayUtils() {
    }

    /**
     * int の一覧からバイト配列を生成します。
     *
     * @param values int の一覧
     * @return バイト配列
     */
    public static byte[] bytes(int... values) {
        byte[] bytes = new byte[values.length];
        for (int i = 0; i < values.length; ++i) {
            bytes[i] = (byte) values[i];
        }
        return bytes;
    }

    /**
     * バイト配列を16進数文字列に変換します。
     *
     * @param a バイト配列
     * @return 16進数文字列
     */
    public static String toHexString(byte[] a) {
        StringWriter sw = new StringWriter(a.length * 2);
        PrintWriter pw = new PrintWriter(sw);
        for (int i = 0; i < a.length; ++i) {
            pw.printf("%02X", a[i]);
        }
        pw.flush();
        return sw.toString();
    }

}
