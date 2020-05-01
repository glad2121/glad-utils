package org.glad2121.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 変換表に基づいてコードポイントの変換を行うコンバータ。
 *
 * @author glad2121
 */
class CodePointConverter {

    /**
     * {@code Logger}
     */
    static final Logger logger = LoggerFactory.getLogger(CodePointConverter.class);

    /**
     * コンバータの名前。
     */
    final String name;

    /**
     * 変換表。
     */
    final Map<Integer, Integer> map;

    /**
     * 基本多言語面（BMP）のみか。
     */
    final boolean bmpOnly;

    /**
     * コンストラクタ。
     *
     * @param name コンバータの名前
     * @param map 変換表
     */
    CodePointConverter(String name, Map<Integer, Integer> map) {
        this.name = name;
        this.map = Collections.unmodifiableMap(map);
        this.bmpOnly = map.entrySet().stream().allMatch(entry ->
            Character.isBmpCodePoint(entry.getKey())
                    && Character.isBmpCodePoint(entry.getValue()));
    }

    /**
     * 指定された情報でコンバータを生成します。
     *
     * @param name コンバータの名前
     * @param from 変換元のコードポイント一覧
     * @param to   変換先のコードポイント一覧
     * @return コンバータ
     */
    static CodePointConverter create(String name, int[] from, int[] to) {
        Objects.requireNonNull(from, () -> String.format("[%s] from", name));
        Objects.requireNonNull(to, () -> String.format("[%s] to", name));
        if (from.length != to.length) {
            throw new IllegalArgumentException(String.format(
                    "[%s] Length must be same: from.length = %s, to.length = %d.",
                    name, from.length, to.length));
        }
        Map<Integer, Integer> map = new HashMap<>(from.length);
        for (int i = 0; i < from.length; ++i) {
            if (map.containsKey(from[i])) {
                if (logger.isWarnEnabled()) {
                    logger.warn("[{}] Duplicated key: {}",
                            name, NumberUtils.toHexString(from[i], 4));
                }
            } else {
                map.put(from[i], to[i]);
            }
        }
        return new CodePointConverter(name, map);
    }

    /**
     * 指定された情報でコンバータを生成します。
     *
     * @param name コンバータの名前
     * @param from 変換元のコードポイント一覧
     * @param to   変換先のコードポイント一覧
     * @return コンバータ
     */
    static CodePointConverter create(String name, String from, String to) {
        Objects.requireNonNull(from, () -> String.format("[%s] from", name));
        Objects.requireNonNull(to, () -> String.format("[%s] to", name));
        return create(name, from.codePoints().toArray(), to.codePoints().toArray());
    }

    /**
     * コードポイントを変換します。
     *
     * @param c 変換元のコードポイント
     * @return   変換後のコードポイント
     */
    public int convert(int c) {
        return map.getOrDefault(c, c);
    }

    /**
     * 文字列を変換します。
     *
     * @param s 変換元の文字列
     * @return   変換後の文字列
     */
    public String convert(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        if (bmpOnly) {
            // 効率のため、すべて BMP ならば char で処理する。
            int length = s.length();
            char[] chars = new char[length];
            for (int i = 0; i < length; ++i) {
                chars[i] = (char) convert(s.charAt(i));
            }
            return String.valueOf(chars);
        } else {
            return StringUtils.codePointsToString(
                    s.codePoints().map(this::convert).toArray());
        }
    }

}
