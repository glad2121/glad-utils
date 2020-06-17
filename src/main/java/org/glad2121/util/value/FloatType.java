package org.glad2121.util.value;

/**
 * {@code Float} 型。
 *
 * @author glad2121
 */
class FloatType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    public FloatType(Float defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Float} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Float convert(Object o) {
        return toFloat(o);
    }

    /**
     * オブジェクトを {@code Float} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Float toFloat(Object o) {
        if (o instanceof Number) {
            return toFloat((Number) o);
        }
        return toFloat(String.valueOf(o));
    }

    /**
     * 数値を {@code Float} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static Float toFloat(Number n) {
        if (n instanceof Float) {
            return (Float) n;
        }
        return n.floatValue();
    }

    /**
     * 文字列を {@code float} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static float toFloat(String s) {
        return Float.parseFloat(s);
    }

}
