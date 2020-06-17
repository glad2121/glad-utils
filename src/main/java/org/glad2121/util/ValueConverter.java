package org.glad2121.util;

import java.util.Optional;

import org.glad2121.util.value.ValueTypes;

/**
 * 値のコンバータ。
 *
 * @author glad2121
 */
public class ValueConverter {

    /**
     * 使用しないコンストラクタ。
     */
    private ValueConverter() {
    }

    /**
     * オブジェクトを指定されたデータ型に変換します。
     *
     * @param <T>  データ型
     * @param o    オブジェクト
     * @param type データ型
     * @return 変換後の値
     */
    public static <T> T convert(Object o, Class<? extends T> type) {
        if (o == null) {
            return defaultValue(type);
        }
        if (o instanceof Optional) {
            return convert(getOrNull((Optional<?>) o), type);
        }
        return convert0(o, type);
    }

    /**
     * オブジェクトを指定されたデータ型に変換します。
     *
     * @param <T>  データ型
     * @param o    オブジェクト
     * @param type データ型
     * @param defaultValue {@code null} の場合のデフォルト値
     * @return 変換後の値
     */
    public static <T> T convert(Object o, Class<? extends T> type, T defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        if (o instanceof Optional) {
            return convert(getOrNull((Optional<?>) o), type, defaultValue);
        }
        return convert0(o, type);
    }

    /**
     * オブジェクトを指定されたデータ型に変換し、{@code Optional} で返します。
     *
     * @param <T>  データ型
     * @param o    オブジェクト
     * @param type データ型
     * @return 変換後の値を含む {@code Optional}
     */
    public static <T> Optional<T> toOptional(Object o, Class<? extends T> type) {
        if (o == null) {
            return Optional.empty();
        }
        if (o instanceof Optional) {
            return toOptional(getOrNull((Optional<?>) o), type);
        }
        return Optional.ofNullable(convert0(o, type));
    }

    /**
     * 指定されたデータ型のデフォルト値を返します。
     *
     * @param <T>  データ型
     * @param type データ型
     * @return デフォルト値
     */
    static <T> T defaultValue(Class<? extends T> type) {
        return ValueTypes.valueType(type).defaultValue();
    }

    /**
     * オブジェクトを指定されたデータ型に変換します。
     *
     * @param <T>  データ型
     * @param o    オブジェクト
     * @param type データ型
     * @return 変換後の値
     */
    @SuppressWarnings("unchecked")
    static <T> T convert0(Object o, Class<? extends T> type) {
        if (type.isInstance(o)) {
            return (T) o;
        }
        return ValueTypes.valueType(type).convert(o);
    }

    /**
     * 値が存在する場合はその値を、そうでなければ {@code null} を返します。
     *
     * @param optional {@code Optional}
     * @return 保有する値または {@code null}
     */
    static Object getOrNull(Optional<?> optional) {
        return optional.orElse(null);
    }

}
