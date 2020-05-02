package org.glad2121.charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    final Map<Integer, CodeType> map;

    /**
     * コンストラクタ。
     *
     * @param map コードポイントとコード区分の対応表。
     */
    CodePointSet(Map<Integer, CodeType> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    /**
     * 指定されたコードポイントのコード区分を返します。
     *
     * @param codePoint コードポイント
     * @return コード区分
     */
    public CodeType codeType(int codePoint) {
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
     * @param types コード区分
     * @return コード区分に含まれれば {@code true}
     */
    public boolean contains(int codePoint, CodeType... types) {
        CodeType ct = codeType(codePoint);
        for (CodeType type: types) {
            if (ct == type) {
                return true;
            }
        }
        return false;
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
     * @param types コード区分
     * @return すべてコード区分に含まれれば {@code true}
     */
    public boolean containsAll(CharSequence s, CodeType... types) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.codePoints().allMatch(cp -> contains(cp, types));
    }

    /**
     * 指定されたコード区分のコードポイントの個数を返します。
     *
     * @param type コード区分
     * @return コードポイントの個数
     */
    int count(CodeType type) {
        return (int) map.entrySet().stream()
            .filter(entry -> entry.getValue() == type)
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
            Map<Integer, CodeType> map = new HashMap<>();
            Pattern p = Pattern.compile(
                    "(\\d),\\\\u([0-9A-F]+)(?:-\\\\u([0-9A-F]+)|\\\\u([0-9A-F]+))?");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    CodeType type = CodeType.values()[Integer.parseInt(m.group(1))];
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
         * 2: JIS X 0201 (半角カナ)
         */
        JIS_X_0201,

        /**
         * 3: JIS X 0208 (第1水準漢字、第2水準漢字、非漢字)。
         */
        JIS_X_0208,

        /**
         * 4: NEC 特殊文字。
         */
        NEC_SPECIAL_CHAR,

        /**
         * 5: IBM 拡張漢字。
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
