package org.glad2121.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code boolean / Boolean} 型
 *
 * @author glad2121
 */
class BooleanType extends ValueType {

    /**
     * 文字列と {@code boolean} の変換表。
     */
    static final Map<String, Boolean> STR_TO_BOOL;

    static {
        Map<String, Boolean> map = new HashMap<>();
        map.put("true", true);
        map.put("false", false);
        map.put("yes", true);
        map.put("no", false);
        map.put("on", true);
        map.put("off", false);
        map.put("t", true);
        map.put("f", false);
        map.put("y", true);
        map.put("n", false);
        map.put("1", true);
        map.put("0", false);
        STR_TO_BOOL = Collections.unmodifiableMap(map);
    }

    /**
     * コンストラクタ。
     *
     * @param defaultValue 初期値
     */
    public BooleanType(Boolean defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Boolean} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Boolean convert(Object o) {
        return toBoolean(o);
    }

    /**
     * オブジェクトを {@code Boolean} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Boolean toBoolean(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof Character) {
            return toBoolean((char) o);
        }
        if (o instanceof Number) {
            return toBoolean((Number) o);
        }
        return toBoolean(String.valueOf(o));
    }

    /**
     * 文字を {@code boolean} に変換します。
     * <p>
     * {@code t / y / 1} を {@code true}、{@code f / n / 0} を {@code false} と判定し、
     * それ以外の値は例外をスローする。
     *
     * @param c 文字
     * @return 変換後の値
     */
    static boolean toBoolean(char c) {
        if ("TtYy1".indexOf(c) >= 0) {
            return true;
        }
        if ("FfNn0".indexOf(c) >= 0) {
            return false;
        }
        throw new IllegalArgumentException(String.format("¥¥u%04X [%s]", c, c));
    }

    /**
     * 数値を {@code boolean} に変換します。
     * <p>
     * {@code 0} を {@code false}、それ以外を {@code true} と判定する。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static boolean toBoolean(Number n) {
        return LongType.toLong(n) != 0L;
    }

    /**
     * 文字列を {@code boolean} に変換します。
     * <p>
     * {@code true / yes / on / 1} を {@code true}、{@code false / no / off / 0} を {@code false}
     * と判定し、それ以外の値は例外をスローする。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static boolean toBoolean(String s) {
        Boolean b = STR_TO_BOOL.get(s.toLowerCase());
        if (b != null) {
            return b;
        }
        throw new IllegalArgumentException(s);
    }

}
