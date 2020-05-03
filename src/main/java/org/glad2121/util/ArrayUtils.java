package org.glad2121.util;

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

}
