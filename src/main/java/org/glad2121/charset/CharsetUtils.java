package org.glad2121.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.regex.Pattern;

import org.glad2121.charset.CodePointSet.CodeType;
import org.glad2121.util.StringUtils;

/**
 * 文字集合関連のユーティリティ。
 *
 * @author glad2121
 */
public final class CharsetUtils {

    /**
     * ISO-2022-JP
     */
    public static final Charset ISO_2022_JP = Charset.forName("ISO-2022-JP");

    /**
     * EUC-JP
     */
    public static final Charset EUC_JP = Charset.forName("EUC-JP");

    /**
     * Shift_JIS
     */
    public static final Charset SHIFT_JIS = Charset.forName("Shift_JIS");

    /**
     * Shift_JIS-2004
     */
    public static final Charset SHIFT_JIS_2004 = Charset.forName("x-SJIS_0213");

    /**
     * Windows-31J
     */
    public static final Charset WINDOWS_31J = Charset.forName("Windows-31J");

    /**
     * IBM-942
     */
    public static final Charset IBM_942 = Charset.forName("x-IBM942");

    /**
     * IBM-943
     */
    public static final Charset IBM_943 = Charset.forName("x-IBM943");

    /**
     * IBM-930
     */
    public static final Charset IBM_930 = Charset.forName("x-IBM930");

    /**
     * IBM-939
     */
    public static final Charset IBM_939 = Charset.forName("x-IBM939");

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
     * 利用可能な文字のうち、JIS X 0213:2004 に含まれない文字。
     */
    static final String NON_JIS_2004 =
            "∑"
            + "伹俍俿僘兤冾劜勀卲叝坥墲奓奣妺巐弡恝惞愑"
            + "戓朎昮汯浯涖渹猤玽珒珵琩硺羡菶蕫譓軏遧釞"
            + "鈆鉷鋕鎤鏸鐱鑈靃餧鮻"
            + "仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽"
            + "蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴";

    /**
     * 使用しないコンストラクタ。
     */
    private CharsetUtils() {
    }

    /**
     * 文字列を構成する文字がすべて ASCII の数字か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII の数字ならば {@code true}
     */
    public static boolean isAsciiNumeric(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAsciiNumeric);
    }

    /**
     * 指定されたコードポイントが ASCII の数字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII の数字ならば {@code true}
     */
    static boolean isAsciiNumeric(int codePoint) {
        return ('0' <= codePoint) && (codePoint <= '9');
    }

    /**
     * 文字列を構成する文字がすべて ASCII の英大文字か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII の英大文字ならば {@code true}
     */
    public static boolean isAsciiUpper(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAsciiUpper);
    }

    /**
     * 指定されたコードポイントが ASCII の英大文字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII の英大文字ならば {@code true}
     */
    static boolean isAsciiUpper(int codePoint) {
        return ('A' <= codePoint) && (codePoint <= 'Z');
    }

    /**
     * 文字列を構成する文字がすべて ASCII の英小文字か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII の英小文字ならば {@code true}
     */
    public static boolean isAsciiLower(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAsciiLower);
    }

    /**
     * 指定されたコードポイントが ASCII の英小文字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII の英小文字ならば {@code true}
     */
    static boolean isAsciiLower(int codePoint) {
        return ('a' <= codePoint) && (codePoint <= 'z');
    }

    /**
     * 文字列を構成する文字がすべて ASCII の英字か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII の英字ならば {@code true}
     */
    public static boolean isAsciiAlpha(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAsciiAlpha);
    }

    /**
     * 指定されたコードポイントが ASCII の英字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII の英字ならば {@code true}
     */
    static boolean isAsciiAlpha(int codePoint) {
        return isAsciiUpper(codePoint) || isAsciiLower(codePoint);
    }

    /**
     * 文字列を構成する文字がすべて ASCII の英数字か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII の英数字ならば {@code true}
     */
    public static boolean isAsciiAlnum(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAsciiAlnum);
    }

    /**
     * 指定されたコードポイントが ASCII の英数字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII の英数字ならば {@code true}
     */
    static boolean isAsciiAlnum(int codePoint) {
        return isAsciiNumeric(codePoint) || isAsciiAlpha(codePoint);
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
     * 文字列を構成する文字がすべて全角数字か判定します。
     *
     * @param s 文字列
     * @return すべて全角数字ならば {@code true}
     */
    public static boolean isFullwidthNumeric(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isFullwidthNumeric);
    }

    /**
     * 指定されたコードポイントが全角数字か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角数字ならば {@code true}
     */
    static boolean isFullwidthNumeric(int codePoint) {
        return ('０' <= codePoint) && (codePoint <= '９');
    }

    /**
     * 文字列を構成する文字がすべて全角英大文字か判定します。
     *
     * @param s 文字列
     * @return すべて全角英大文字ならば {@code true}
     */
    public static boolean isFullwidthUpper(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isFullwidthUpper);
    }

    /**
     * 指定されたコードポイントが全角英大文字か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角英大文字ならば {@code true}
     */
    static boolean isFullwidthUpper(int codePoint) {
        return ('Ａ' <= codePoint) && (codePoint <= 'Ｚ');
    }

    /**
     * 文字列を構成する文字がすべて全角英小文字か判定します。
     *
     * @param s 文字列
     * @return すべて全角英小文字ならば {@code true}
     */
    public static boolean isFullwidthLower(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isFullwidthLower);
    }

    /**
     * 指定されたコードポイントが全角英小文字か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角英小文字ならば {@code true}
     */
    static boolean isFullwidthLower(int codePoint) {
        return ('a' <= codePoint) && (codePoint <= 'ｚ');
    }

    /**
     * 文字列を構成する文字がすべて全角英字か判定します。
     *
     * @param s 文字列
     * @return すべて全角英字ならば {@code true}
     */
    public static boolean isFullwidthAlpha(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isFullwidthAlpha);
    }

    /**
     * 指定されたコードポイントが全角英字か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角英字ならば {@code true}
     */
    static boolean isFullwidthAlpha(int codePoint) {
        return isFullwidthUpper(codePoint) || isFullwidthLower(codePoint);
    }

    /**
     * 文字列を構成する文字がすべて全角英数字か判定します。
     *
     * @param s 文字列
     * @return すべて全角英数字ならば {@code true}
     */
    public static boolean isFullwidthAlnum(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isFullwidthAlnum);
    }

    /**
     * 指定されたコードポイントが全角英数字か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角英数字ならば {@code true}
     */
    static boolean isFullwidthAlnum(int codePoint) {
        return isFullwidthNumeric(codePoint) || isFullwidthAlpha(codePoint);
    }

    /**
     * 指定されたコードポイントが ASCII と互換性のある全角文字か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII と互換性のある全角文字ならば {@code true}
     */
    static boolean isFullwidthCompatibleAscii(int codePoint) {
        return ('\uFF01' <= codePoint) && (codePoint <= '\uFF5E');
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
     * 文字列を構成する文字がすべて ASCII か判定します。
     *
     * @param s 文字列
     * @return すべて ASCII ならば {@code true}
     */
    public static boolean isAscii(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isAscii);
    }

    /**
     * 指定されたコードポイントが ASCII か判定します。
     *
     * @param codePoint コードポイント
     * @return ASCII ならば {@code true}
     */
    static boolean isAscii(int codePoint) {
        return CodePointSet.INSTANCE.contains(codePoint, CodeType.US_ASCII);
    }

    /**
     * 文字列を構成する文字がすべて JIS X 0201 か判定します。
     * <p>
     * 以下の文字が該当します。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0201 (半角カナ)</li>
     * </ul>
     *
     * @param s 文字列
     * @return すべて JIS X 0201 ならば {@code true}
     */
    static boolean isJisX0201(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isJisX0201);
    }

    /**
     * 指定されたコードポイントが JIS X 0201 か判定します。
     *
     * @param codePoint コードポイント
     * @return JIS X 0201 ならば {@code true}
     */
    static boolean isJisX0201(int codePoint) {
        return CodePointSet.INSTANCE.contains(codePoint,
                CodeType.US_ASCII, CodeType.JIS_X_0201);
    }

    /**
     * 文字列を構成する文字がすべて JIS 1990 か判定します。
     * <p>
     * 以下の文字が該当します。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0201 (半角カナ)</li>
     *   <li>JIS X 0208-1990 (非漢字、第1水準漢字、第2水準漢字)</li>
     * </ul>
     *
     * @param s 文字列
     * @return すべて JIS 1990 ならば {@code true}
     */
    public static boolean isJis1990(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isJis1990);
    }

    /**
     * 指定されたコードポイントが JIS 1990 か判定します。
     *
     * @param codePoint コードポイント
     * @return JIS 1990 ならば {@code true}
     */
    static boolean isJis1990(int codePoint) {
        return CodePointSet.INSTANCE.contains(codePoint,
                CodeType.US_ASCII, CodeType.JIS_X_0201, CodeType.JIS_X_0208);
    }

    /**
     * 文字列を構成する文字がすべて JIS 2004 か判定します。
     * <p>
     * 以下の文字が該当します。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0201 (半角カナ)</li>
     *   <li>JIS X 0208-1990 (非漢字、第1水準漢字、第2水準漢字)</li>
     *   <li>JIS X 0213:2004 (非漢字、第3水準漢字、第4水準漢字)</li>
     * </ul>
     *
     * @param s 文字列
     * @return すべて JIS 2004 ならば {@code true}
     */
    public static boolean isJis2004(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.codePoints().allMatch(CharsetUtils::isJis2004);
    }

    /**
     * 指定されたコードポイントが JIS 2004 か判定します。
     *
     * @param codePoint コードポイント
     * @return JIS 2004 ならば {@code true}
     */
    static boolean isJis2004(int codePoint) {
        CodeType type = CodePointSet.INSTANCE.codeType(codePoint);
        return (type != null)
                && (type.compareTo(CodeType.JIS_X_0208) <= 0
                || CodeType.JIS_X_0213_3.compareTo(type) <= 0
                || NON_JIS_2004.indexOf(codePoint) < 0);
    }

    /**
     * 文字列を構成する文字がすべて基本日本文字集合か判定します。
     * <p>
     * 以下の文字が該当します。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0208-1990 (ASCII と互換性のある全角英数記号を除く)</li>
     * </ul>
     *
     * @param s 文字列
     * @return すべて基本日本文字集合ならば {@code true}
     */
    public static boolean isBasicJ(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isBasicJ);
    }

    /**
     * 指定されたコードポイントが基本日本文字集合か判定します。
     *
     * @param codePoint コードポイント
     * @return 基本日本文字集合ならば {@code true}
     */
    static boolean isBasicJ(int codePoint) {
        return CodePointSet.INSTANCE.contains(codePoint,
                CodeType.US_ASCII, CodeType.JIS_X_0208)
                && !isFullwidthCompatibleAscii(codePoint);
    }

    /**
     * 文字列を構成する文字がすべて通用日本文字集合か判定します。
     * <p>
     * 以下の文字が該当します（Windows-31J と同等）。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0201 (半角カナ)</li>
     *   <li>JIS X 0208-1990 (非漢字、第1水準漢字、第2水準漢字)</li>
     *   <li>NEC特殊文字</li>
     *   <li>IBM拡張文字</li>
     * </ul>
     *
     * @param s 文字列
     * @return すべて通用日本文字集合ならば {@code true}
     */
    public static boolean isCommonJ(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isCommonJ);
    }

    /**
     * 指定されたコードポイントが通用日本文字集合か判定します。
     *
     * @param codePoint コードポイント
     * @return 通用日本文字集合ならば {@code true}
     */
    static boolean isCommonJ(int codePoint) {
        return CodePointSet.INSTANCE.contains(codePoint,
                CodeType.US_ASCII, CodeType.JIS_X_0201, CodeType.JIS_X_0208,
                CodeType.NEC_SPECIAL_CHAR, CodeType.IBM_EXT);
    }

    /**
     * 文字列を構成する文字がすべて利用可能な文字か判定します。
     * <p>
     * 以下の文字が該当します。
     * <ul>
     *   <li>US-ASCII</li>
     *   <li>JIS X 0201 (半角カナ)</li>
     *   <li>JIS X 0208-1990 (非漢字、第1水準漢字、第2水準漢字)</li>
     *   <li>JIS X 0213:2004 (非漢字、第3水準漢字、第4水準漢字)</li>
     *   <li>NEC特殊文字</li>
     *   <li>IBM拡張文字</li>
     * </ul>
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

    /**
     * 文字列をバイト配列にエンコードします。
     *
     * @param s 文字列
     * @param charset 文字コード
     * @return バイト配列
     */
    public static byte[] encode(CharSequence s, Charset charset) {
        try {
            return charset.newEncoder().encode(CharBuffer.wrap(s)).array();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(e);
        }
    }

    /**
     * 文字列をバイト配列にエンコードします。
     *
     * @param s 文字列
     * @param charset 文字コード
     * @param replace 変換できない文字を置き換えるバイト配列
     * @return バイト配列
     */
    public static byte[] encode(CharSequence s, Charset charset, byte[] replace) {
        try {
            return charset.newEncoder()
                .replaceWith(replace)
                .encode(CharBuffer.wrap(s))
                .array();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(e);
        }
    }

    /**
     * バイト配列を文字列にデコードします。
     *
     * @param bytes バイト配列
     * @param charset 文字コード
     * @return 文字列
     */
    public static String decode(byte[] bytes, Charset charset) {
        try {
            return charset.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(e);
        }
    }

    /**
     * バイト配列を文字列にデコードします。
     *
     * @param bytes バイト配列
     * @param charset 文字コード
     * @param replace 変換できない文字を置き換える文字列
     * @return 文字列
     */
    public static String decode(byte[] bytes, Charset charset, String replace) {
        try {
            return charset.newDecoder()
                .replaceWith(replace)
                .decode(ByteBuffer.wrap(bytes))
                .toString();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(e);
        }
    }

}
