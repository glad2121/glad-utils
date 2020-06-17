package org.glad2121.util.value;

/**
 * {@code Double} 型。
 *
 * @author glad2121
 */
class DoubleType extends ValueType {

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    public DoubleType(Double defaultValue) {
        super(defaultValue);
    }

    /**
     * オブジェクトを {@code Double} に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public Double convert(Object o) {
        return toDouble(o);
    }

    /**
     * オブジェクトを {@code Double} に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static Double toDouble(Object o) {
        if (o instanceof Number) {
            return toDouble((Number) o);
        }
        return toDouble(String.valueOf(o));
    }

    /**
     * 数値を {@code Double} に変換します。
     *
     * @param n 数値
     * @return 変換後の値
     */
    static Double toDouble(Number n) {
        if (n instanceof Double) {
            return (Double) n;
        }
        return n.doubleValue();
    }

    /**
     * 文字列を {@code double} に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static double toDouble(String s) {
        return Double.parseDouble(s);
    }

}
