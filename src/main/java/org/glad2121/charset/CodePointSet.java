package org.glad2121.charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glad2121.util.StringUtils;

/**
 * コードポイントの集合。
 *
 * @author glad2121
 */
class CodePointSet {

    /**
     * 設定ファイル名。
     */
    static final String SOURCE_NAME = "charset-compact.txt";

    /**
     * このクラスのインスタンス。
     */
    static final CodePointSet INSTANCE = loadFromCharsetCompactText(SOURCE_NAME);

    /**
     * コードポイントとコード区分の対応表。
     */
    final Map<Integer, Integer> map;

    /**
     * コンストラクタ。
     *
     * @param map コードポイントとコード区分の対応表。
     */
    CodePointSet(Map<Integer, Integer> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    /**
     * 指定されたコードポイントのコード区分を返します。
     *
     * @param codePoint コードポイント
     * @return コード区分
     */
    public Integer codeType(int codePoint) {
        return map.get(codePoint);
    }

    /**
     * 指定されたコードポイントが対応表に含まれるか判定します。
     *
     * @param codePoint コードポイント
     * @return 対応表に含まれれば {@code true}
     */
    public boolean contains(int codePoint) {
        return map.containsKey(codePoint);
    }

    /**
     * 指定されたコードポイントがコード区分に含まれるか判定します。
     *
     * @param codePoint コードポイント
     * @param pred コード区分
     * @return コード区分に含まれれば {@code true}
     */
    public boolean contains(int codePoint, IntPredicate pred) {
        Integer ct = codeType(codePoint);
        return (ct != null) && pred.test(ct);
    }

    /**
     * 文字列を構成する文字がすべて対応表に含まれるか判定します。
     *
     * @param s 文字列
     * @return すべて対応表に含まれれば {@code true}
     */
    public boolean containsAll(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.codePoints().allMatch(this::contains);
    }

    /**
     * 文字列を構成する文字がすべてコード区分に含まれるか判定します。
     *
     * @param s 文字列
     * @param pred コード区分
     * @return すべてコード区分に含まれれば {@code true}
     */
    public boolean containsAll(CharSequence s, IntPredicate pred) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.codePoints().allMatch(cp -> contains(cp, pred));
    }

    /**
     * 指定されたコード区分のコードポイントの個数を返します。
     *
     * @param pred コード区分
     * @return コードポイントの個数
     */
    int count(IntPredicate pred) {
        return (int) map.entrySet().stream()
            .filter(entry -> pred.test(entry.getValue()))
            .count();
    }

    /**
     * 設定ファイルを読み込んで、コードポイントの集合を生成します。
     *
     * @param name リソース名
     * @return コードポイントの集合
     */
    static CodePointSet loadFromCharsetCompactText(String name) {
        try (InputStream in = CodePointSet.class.getResourceAsStream(name)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + name);
            }
            Map<Integer, Integer> map = new HashMap<>();
            Pattern p = Pattern.compile(
                    "(\\d{2}),\\\\u([0-9A-F]+)(?:-\\\\u([0-9A-F]+)|\\\\u([0-9A-F]+))?");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    int type = Integer.parseInt(m.group(1), 16);
                    int first = Integer.parseInt(m.group(2), 16);
                    if (m.group(4) != null) {
                        // サロゲートペア。
                        int low = Integer.parseInt(m.group(4), 16);
                        map.put(Character.toCodePoint((char) first, (char) low), type);
                    } else if (m.group(3) != null) {
                        // コードポイントの範囲。
                        int last = Integer.parseInt(m.group(3), 16);
                        for (int cp = first; cp <= last; ++cp) {
                            map.put(cp, type);
                        }
                    } else {
                        // 単独のコードポイント。
                        map.put(first, type);
                    }
                }
            }
            return new CodePointSet(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 11: ASCII (制御文字を除く)。
     */
    static boolean isAscii(int codeType) {
        return codeType == 0x11;
    }

    /**
     * 2x: JIS X 0201 (ASCII との重複を除く)。
     */
    static boolean isJisX0201Ext(int codeType) {
        return (codeType & 0xF0) == 0x20;
    }

    /**
     * 1x-2x: JIS X 0201 (ASCII を含む)。
     */
    static boolean isJisX0201(int codeType) {
        int ct1 = codeType & 0xF0;
        return (0x10 <= ct1) && (ct1 <= 0x20);
    }

    /**
     * 3x: JIS X 0208 (非漢字、第1水準漢字、第2水準漢字)。
     */
    static boolean isJisX0208(int codeType) {
        return (codeType & 0xF0) == 0x30;
    }

    /**
     * 4x: JIS X 0213 1面 (JIS X 0208 との重複を除く)。
     */
    static boolean isJisX0213P1Ext(int codeType) {
        return (codeType & 0xF0) == 0x40;
    }

    /**
     * 3x-4x: JIS X 0213 1面 (JIS X 0208 を含む)。
     */
    static boolean isJisX0213P1(int codeType) {
        int ct1 = codeType & 0xF0;
        return (0x30 <= ct1) && (ct1 <= 0x40);
    }

    /**
     * 5x: JIS X 0213 2面。
     */
    static boolean isJisX0213P2(int codeType) {
        return (codeType & 0xF0) == 0x50;
    }

    /**
     * 3x-5x: JIS X 0213。
     */
    static boolean isJisX0213(int codeType) {
        int ct1 = codeType & 0xF0;
        return (0x30 <= ct1) && (ct1 <= 0x50);
    }

    /**
     * x3: NEC特殊文字。
     */
    static boolean isNecSpecialChar(int codeType) {
        return (codeType & 0x0F) == 0x03;
    }

    /**
     * x4: IBM拡張文字。
     */
    static boolean isIbmExt(int codeType) {
        return (codeType & 0x0F) == 0x04;
    }

    /**
     * 1x-3x: JIS1990。
     */
    static boolean isJis1990(int codeType) {
        int ct1 = codeType & 0xF0;
        return (0x10 <= ct1) && (ct1 <= 0x30);
    }

    /**
     * 1x-5x: JIS2004。
     */
    static boolean isJis2004(int codeType) {
        int ct1 = codeType & 0xF0;
        return (0x10 <= ct1) && (ct1 <= 0x50);
    }

    /**
     * x1: BasicJ
     */
    static boolean isBasicJ(int codeType) {
        return (codeType & 0x0F) == 0x01;
    }

    /**
     * x2-x4: CommonJ
     */
    static boolean isCommonJ(int codeType) {
        int ct2 = codeType & 0x0F;
        return (0x01 <= ct2) && (ct2 <= 0x04);
    }

    /**
     * コード区分。
     */
    enum CodeType {

        /**
         * 0: 未設定。
         */
        UNKNOWN,

        /**
         * 1: US-ASCII (制御文字を覗く)。
         */
        US_ASCII,

        /**
         * 2: JIS X 0201 (半角カナ)。
         */
        JIS_X_0201,

        /**
         * 3: JIS X 0208 (第1水準漢字、第2水準漢字、非漢字)。
         */
        JIS_X_0208,

        /**
         * 4: Windows-31J (NEC 特殊文字)。
         */
        NEC_SPECIAL_CHAR,

        /**
         * 5: Windows-31J (IBM 拡張漢字)。
         */
        IBM_EXT,

        /**
         * 6: JIS X 0213 (Windows-31J を除く、第3水準漢字、非漢字)。
         */
        JIS_X_0213_3,

        /**
         * 7: JIS X 0213 (Windows-31J を除く、第4水準漢字)。
         */
        JIS_X_0213_4

    }

}
