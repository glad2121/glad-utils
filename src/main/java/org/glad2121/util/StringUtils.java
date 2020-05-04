package org.glad2121.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列関連のユーティリティ。
 *
 * @author glad2121
 */
public final class StringUtils {

    /**
     * 使用しないコンストラクタ。
     */
    private StringUtils() {
    }

    /**
     * 文字列が {@code null} または空文字列であるか判定します。
     *
     * @param s 文字列
     * @return {@code null} または空文字列ならば {@code true}
     */
    public static boolean isEmpty(CharSequence s) {
        return (s == null) || (s.length() == 0);
    }

    /**
     * 文字列が {@code null} でも空文字列でもないか判定します。
     *
     * @param s 文字列
     * @return {@code null} でも空文字列でもなければ {@code true}
     */
    public static boolean isNotEmpty(CharSequence s) {
        return !isEmpty(s);
    }

    /**
     * コードポイントの配列を文字列に変換します。
     *
     * @param codePoints コードポイントの配列
     * @return 文字列
     */
    public static String codePointsToString(int... codePoints) {
        return new String(codePoints, 0, codePoints.length);
    }

    /**
     * 文字列を Unicode エスケープに変換します。
     *
     * @param s 文字列
     * @return Unicode エスケープ
     */
    public static String toUnicodeEscape(CharSequence s) {
        StringWriter sw = new StringWriter(s.length());
        PrintWriter pw = new PrintWriter(sw);
        for (int i = 0; i < s.length(); ++i) {
            pw.printf("\\u%04X", (int) s.charAt(i));
        }
        pw.flush();
        return sw.toString();
    }

    /**
     * 文字列のパターンにマッチした部分を関数の結果で置き換えます。
     *
     * @param s 文字列
     * @param p パターン
     * @param f 変換関数
     * @return 変換後の文字列
     */
    public static String replaceAll(CharSequence s, Pattern p, Function<String, String> f) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        Matcher m = p.matcher(s);
        int length = s.length();
        StringBuilder sb = new StringBuilder(length);
        int index = 0;
        while (m.find()) {
            // マッチしなかった部分をそのまま追加。
            sb.append(s.subSequence(index, m.start()));
            // マッチした部分を関数で変換して追加。
            sb.append(f.apply(m.group()));
            index = m.end();
        }
        // 残りをそのまま追加。
        sb.append(s.subSequence(index, length));
        return sb.toString();
    }

}
