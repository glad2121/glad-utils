package org.glad2121.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * 文字集合関連のユーティリティ。
 *
 * @author glad2121
 */
public final class CharsetUtils {

    /**
     * 半角カタカナのパターン。
     */
    static final Pattern HALFWIDTH_KATAKANA_PATTERN =
            Pattern.compile("[\uFF65-\uFF9F]+");

    /**
     * 半角カナのパターン。
     */
    static final Pattern HALFWIDTH_KANA_PATTERN =
            Pattern.compile("[\uFF61-\uFF9F]+");

    /**
     * 全角カタカナのパターン。
     */
    static final Pattern FULLWIDTH_KATAKANA_PATTERN =
            Pattern.compile("[\u30A0-\u30FE\u3099-\u309C]+");

    /**
     * 全角ひらがなのパターン。
     */
    static final Pattern FULLWIDTH_HIRAGANA_PATTERN =
            Pattern.compile("[\u3041-\u3096\u3099-\u309E\u30A0\u30FB\u30FC]+");

    /**
     * 全角カタカナ・ひらがなのパターン。
     */
    static final Pattern FULLWIDTH_KANA_PATTERN =
            Pattern.compile("[　、。「」\u3041-\u3096\u3099-\u309E\u30A0-\u30FE]+");

    /**
     * 半角カナと互換性のある、全角カタカナ・ひらがなのパターン。
     * <p>
     * {@link FULLWIDTH_KANA_PATTERN} から変換に問題がる文字を除いたもの。
     */
    static final Pattern FULLWIDTH_COMPATIBLE_KANA_PATTERN =
            Pattern.compile("[　、。「」\u3041-\u3096\u3099-\u309A\u30A0-\u30F7\u30FA-\u30FC]+");

    /**
     * 半角カナ・全角カタカナ・ひらがなのパターン。
     */
    static final Pattern KANA_PATTERN =
            Pattern.compile("[、。「」\u3041-\u3096\u3099-\u309E\u30A1-\u30FE\uFF61-\uFF9F]+");

    /**
     * 使用しないコンストラクタ。
     */
    private CharsetUtils() {
    }

    /**
     * 文字列を構成する文字がすべて半角カタカナか判定します。
     *
     * @param s 文字列
     * @return すべて半角カタカナならば {@code true}
     */
    public static boolean isHalfwidthKatakana(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return HALFWIDTH_KATAKANA_PATTERN.matcher(s).matches();
    }

    /**
     * 文字列を構成する文字がすべて半角カナか判定します。
     *
     * @param s 文字列
     * @return すべて半角カナならば {@code true}
     */
    public static boolean isHalfwidthKana(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return HALFWIDTH_KANA_PATTERN.matcher(s).matches();
    }

    /**
     * 文字列を構成する文字がすべて全角カタカナか判定します。
     *
     * @param s 文字列
     * @return すべて全角カタカナならば {@code true}
     */
    public static boolean isFullwidthKatakana(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return FULLWIDTH_KATAKANA_PATTERN.matcher(s).matches();
    }

    /**
     * 文字列を構成する文字がすべて全角ひらがなか判定します。
     *
     * @param s 文字列
     * @return すべて全角ひらがなならば {@code true}
     */
    public static boolean isFullwidthHiragana(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return FULLWIDTH_HIRAGANA_PATTERN.matcher(s).matches();
    }

    /**
     * 文字列を構成する文字がすべて利用可能な文字か判定します。
     *
     * @param s 文字列
     * @return すべて利用可能な文字ならば {@code true}
     */
    public static boolean isAvailable(CharSequence s) {
        return CodePointSet.INSTANCE.containsAll(s);
    }

    /**
     * 文字列中の全角カタカナ・ひらがなを半角カナに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toHalfwidthKana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return StringUtils.replaceAll(s, FULLWIDTH_COMPATIBLE_KANA_PATTERN, g -> {
            // 濁点・半濁点を分解する。
            String decomposed = Normalizer.normalize(g, Normalizer.Form.NFD);
            // 半角カナに変換する。
            return CodePointConverters.TO_HALFWIDTH_KANA.convert(decomposed);
        });
    }

    /**
     * 文字列中の半角カナ・全角ひらがなを全角カタカナに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toFullwidthKatakana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return StringUtils.replaceAll(s, KANA_PATTERN, g -> {
            // 全角カタカナに変換する。
            String converted = CodePointConverters.TO_FULLWIDTH_KATAKANA.convert(g);
            // 濁点・半濁点を結合する。
            return Normalizer.normalize(converted, Normalizer.Form.NFC);
        });
    }

    /**
     * 文字列中の半角カナ・全角カタカナを全角ひらがなに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toFullwidthHiragana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return StringUtils.replaceAll(s, KANA_PATTERN, g -> {
            // 全角カタカナに変換する。
            String converted = CodePointConverters.TO_FULLWIDTH_HIRAGANA.convert(g);
            // 濁点・半濁点を結合する。
            return Normalizer.normalize(converted, Normalizer.Form.NFC);
        });
    }

}
