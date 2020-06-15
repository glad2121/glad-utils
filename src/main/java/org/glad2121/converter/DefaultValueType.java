package org.glad2121.converter;

/**
 * デフォルトの値型。
 *
 * @author glad2121
 */
class DefaultValueType extends ValueType {

    /**
     * オブジェクトを必要な型に変換します。
     * <p>
     * このクラスでは単純にキャストするのみ。
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object o) {
        return (T) o;
    }

}
