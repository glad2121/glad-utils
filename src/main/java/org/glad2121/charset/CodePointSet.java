package org.glad2121.charset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
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
     * コードポイントと文字区分の対応表。
     */
    final Map<Integer, CharType> map;

    /**
     * コンストラクタ。
     *
     * @param map コードポイントと文字区分の対応表。
     */
    CodePointSet(Map<Integer, CharType> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    /**
     * 指定されたコードポイントの文字区分を返します。
     *
     * @param codePoint コードポイント
     * @return 文字区分
     */
    public CharType charType(int codePoint) {
        return map.getOrDefault(codePoint, CharType.UNDEFINED);
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
     * 指定されたコードポイントが条件を満たすか判定します。
     *
     * @param codePoint コードポイント
     * @param pred 条件
     * @return 条件を満たせば {@code true}
     */
    public boolean matches(int codePoint, Predicate<CharType> pred) {
        return pred.test(charType(codePoint));
    }

    /**
     * 文字列を構成する文字がすべて条件を満たすか判定します。
     *
     * @param s 文字列
     * @param pred 条件
     * @return すべて条件を満たせば {@code true}
     */
    public boolean matchesAll(CharSequence s, Predicate<CharType> pred) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.codePoints().allMatch(cp -> matches(cp, pred));
    }

    /**
     * 指定された条件を満たすコードポイントの個数を返します。
     *
     * @param pred 条件
     * @return コードポイントの個数
     */
    int count(Predicate<CharType> pred) {
        return (int) map.entrySet().stream()
            .filter(entry -> pred.test(entry.getValue()))
            .count();
    }

    /**
     * 16バイト2文字からコードポイントを生成します。
     *
     * @param c0 1文字目
     * @param c1 2文字目
     * @return コードポイント
     */
    static int toCodePoint(char c0, char c1) {
        if (Character.isSurrogatePair(c0, c1)) {
            return Character.toCodePoint(c0, c1);
        }
        if (c0 < 0x8000) {
            return (c0 << 16) | c1;
        }
        throw new RuntimeException(String.format("Unsupported code: %04X %04X", c0, c1));
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
            Map<Integer, CharType> map = new HashMap<>();
            Pattern p = Pattern.compile(
                    "(\\d{2}),\\\\u([0-9A-F]+)(?:-\\\\u([0-9A-F]+)|\\\\u([0-9A-F]+))?");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    CharType type = CharType.getOrCreate(Integer.parseInt(m.group(1), 16));
                    int c0 = Integer.parseInt(m.group(2), 16);
                    if (m.group(4) != null) {
                        // サロゲートペアまたは結合文字列。
                        int c1 = Integer.parseInt(m.group(4), 16);
                        map.put(toCodePoint((char) c0, (char) c1), type);
                    } else if (m.group(3) != null) {
                        // コードポイントの範囲。
                        int c1 = Integer.parseInt(m.group(3), 16);
                        for (int cp = c0; cp <= c1; ++cp) {
                            map.put(cp, type);
                        }
                    } else {
                        // 単独のコードポイント。
                        map.put(c0, type);
                    }
                }
            }
            return new CodePointSet(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
