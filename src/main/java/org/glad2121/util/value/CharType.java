package org.glad2121.util.value;

/**
 * 文字型。
 *
 * @author glad2121
 */
class CharType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    public CharType(Character defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを文字に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Character convert(Object o) {
        return toCharacter(o);
    }

    /**
     * オブジェクトを文字に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Character toCharacter(Object o) {
        if (o instanceof Character) {
            return (Character) o;
        }
        if (o instanceof Boolean) {
            return toChar((boolean) o);
        }
        if (o instanceof Number) {
            return toChar((Number) o);
        }
        return toChar(o.toString());
    }

    /**
     * {@code boolean} を文字に変換します。
     *
     * @param b {@code boolean}
     * @return 変換後の値
     */
    static char toChar(boolean b) {
        return b ? 'T': 'F';
    }

    /**
     * 数値を文字に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static char toChar(Number n) {
        if (n instanceof Byte || n instanceof Short) {
            return (char) n.shortValue();
        }
        if (n instanceof Integer || n instanceof Long) {
            int c = IntType.toInteger(n);
            if (Short.MIN_VALUE <= c && c <= 0xFFFF) {
                return (char) c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(n));
    }

    /**
     * 文字列を文字に変換します。
     *
     * @param s 文字列
     * @return 文字
     */
    static char toChar(String s) {
        if (s.length() == 1) {
            return s.charAt(0);
        }
        throw new IllegalArgumentException(s);
    }

}
