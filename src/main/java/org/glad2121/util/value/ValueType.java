package org.glad2121.util.value;

/**
 * 値型の基底クラス。
 *
 * @author glad2121
 */
public abstract class ValueType {

    /**
     * デフォルト値。
     */
    private final Object defaultValue;

    /**
     * コンストラクタ。
     */
    ValueType() {
        this(null);
    }

    /**
     * コンストラクタ。
     *
     * @param defaultValue デフォルト値
     */
    ValueType(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * デフォルト値を返します。
     *
     * @param <T> データ型
     * @return デフォルト値
     */
    @SuppressWarnings("unchecked")
    public <T> T defaultValue() {
        return (T) defaultValue;
    }

    /**
     * オブジェクトをこのクラスが表すデータ型に変換します。
     *
     * @param <T> データ型
     * @param o オブジェクト
     * @return 変換後の値
     */
    public abstract <T> T convert(Object o);

}
