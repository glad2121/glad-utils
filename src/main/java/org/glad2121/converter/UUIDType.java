package org.glad2121.converter;

import java.util.UUID;

/**
 * UUID 型。
 *
 * @author glad2121
 */
class UUIDType extends ValueType {

    /**
     * オブジェクトを UUID に変換します。
     */
    @Override
    @SuppressWarnings("unchecked")
    public UUID convert(Object o) {
        return toUUID(o);
    }

    /**
     * オブジェクトを UUID に変換します。
     *
     * @param o オブジェクト
     * @return 変換後の値
     */
    static UUID toUUID(Object o) {
        if (o instanceof UUID) {
            return (UUID) o;
        }
        return toUUID(o.toString());
    }

    /**
     * 文字列を UUID に変換します。
     *
     * @param s 文字列
     * @return 変換後の値
     */
    static UUID toUUID(String s) {
        return UUID.fromString(s);
    }

}
