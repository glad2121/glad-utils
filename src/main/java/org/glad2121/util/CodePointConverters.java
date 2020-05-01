package org.glad2121.util;

/**
 * 様々なコードポイント変換表。
 *
 * @author glad2121
 */
final class CodePointConverters {

    /**
     * 半角句読点。
     */
    static final String HALFWIDTH_PUNCTATIONS = "､｡｢｣･ｰ";

    /**
     * 半角カタカナ。
     */
    static final String HALFWIDTH_KATAKANA =
            "ｧｨｩｪｫｬｭｮｯｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ";

    /**
     * 全角句読点。
     */
    static final String FULLWIDTH_PUNCTATIONS = "、。「」・ー";

    /**
     * 全角カタカナ。
     */
    static final String FULLWIDTH_KATAKANA =
            "ァィゥェォャュョッ"
            + "アイウエオカキクケコサシスセソタチツテトナニヌネノ"
            + "ハヒフヘホマミムメモヤユヨラリルレロワヲン";

    /**
     * 全角ひらがな。
     */
    static final String FULLWIDTH_HIRAGANA =
            "ぁぃぅぇぉゃゅょっ"
            + "あいうえおかきくけこさしすせそたちつてとなにぬねの"
            + "はひふへほまみむめもやゆよらりるれろわをん";

    /**
     * 全角カタカナ・ひらがなを半角カナに変換するコンバータ。
     */
    static final CodePointConverter TO_HALFWIDTH_KANA = CodePointConverter.create(
            "TO_HALFWIDTH_KANA",
            '　'
            + FULLWIDTH_PUNCTATIONS
            + FULLWIDTH_KATAKANA
            + FULLWIDTH_HIRAGANA
            + '\u3099'  // 濁点（結合文字）
            + '\u309A'  // 半濁点（結合文字）
            + "゛゜",
            ' '
            + HALFWIDTH_PUNCTATIONS
            + HALFWIDTH_KATAKANA
            + HALFWIDTH_KATAKANA
            + "\uFF9E\uFF9F\uFF9E\uFF9F");

    /**
     * 半角カナ・全角ひらがなを全角カタカナに変換するコンバータ。
     */
    static final CodePointConverter TO_FULLWIDTH_KATAKANA = CodePointConverter.create(
            "TO_FULLWIDTH_KATAKANA",
            HALFWIDTH_PUNCTATIONS
            + HALFWIDTH_KATAKANA
            + "\uFF9E\uFF9F"
            + FULLWIDTH_HIRAGANA
            + "がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゎゐゑ\u3094\u3095\u3096ゝゞ",
            FULLWIDTH_PUNCTATIONS
            + FULLWIDTH_KATAKANA
            + '\u3099'  // 濁点（結合文字）
            + '\u309A'  // 半濁点（結合文字）
            + FULLWIDTH_KATAKANA
            + "ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヮヰヱヴヵヶヽヾ");

    /**
     * 半角カナ・全角カタカナを全角ひらがなに変換するコンバータ。
     */
    static final CodePointConverter TO_FULLWIDTH_HIRAGANA = CodePointConverter.create(
            "TO_FULLWIDTH_HIRAGANA",
            HALFWIDTH_PUNCTATIONS
            + HALFWIDTH_KATAKANA
            + "\uFF9E\uFF9F"
            + FULLWIDTH_KATAKANA
            + "ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヮヰヱヴヵヶヽヾ",
            FULLWIDTH_PUNCTATIONS
            + FULLWIDTH_HIRAGANA
            + '\u3099'  // 濁点（結合文字）
            + '\u309A'  // 半濁点（結合文字）
            + FULLWIDTH_HIRAGANA
            + "がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゎゐゑ\u3094\u3095\u3096ゝゞ");

    /**
     * 使用しないコンストラクタ。
     */
    private CodePointConverters() {
    }

}
