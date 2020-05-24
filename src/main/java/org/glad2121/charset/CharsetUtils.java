package org.glad2121.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
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
     * UTF-8
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

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
     * 拡張版 Shift_JIS
     */
    public static final Charset SHIFT_JIS_G = Charset.forName("x-Shift_JIS-g");

    /**
     * Shift_JIS-2004
     */
    public static final Charset SHIFT_JIS_2004 = Charset.forName("x-SJIS_0213");

    /**
     * 拡張版 Shift_JIS-2004
     */
    public static final Charset SHIFT_JIS_2004_G = Charset.forName("x-Shift_JIS-2004-g");

    /**
     * Windows-31J
     */
    public static final Charset WINDOWS_31J = Charset.forName("Windows-31J");

    /**
     * 拡張版 Windows-31J
     */
    public static final Charset WINDOWS_31J_G = Charset.forName("x-Windows-31J-g");

    /**
     * 拡張版 Windows-31J-2004
     */
    public static final Charset WINDOWS_31J_2004_G = Charset.forName("x-Windows-31J-2004-g");

    /**
     * IBM-942 (OS/2)
     */
    public static final Charset IBM_942 = Charset.forName("x-IBM942");

    /**
     * IBM-943 (OS/2、NEC特殊文字対応)
     */
    public static final Charset IBM_943 = Charset.forName("x-IBM943");

    /**
     * IBM-930 (日本語カタカナ漢字)
     */
    public static final Charset IBM_930 = Charset.forName("x-IBM930");

    /**
     * IBM-939 (日本語ラテン文字漢字)
     */
    public static final Charset IBM_939 = Charset.forName("x-IBM939");

    /**
     * 全角ひらがなのパターン。
     */
    static final Pattern FULLWIDTH_HIRAGANA_PATTERN =
            Pattern.compile("[\u3041-\u3096\u3099-\u309E\u30A0\u30FB\u30FC]+");

    /**
     * 全角カタカナのパターン。
     */
    static final Pattern FULLWIDTH_KATAKANA_PATTERN =
            Pattern.compile("[\u30A0-\u30FE\u3099-\u309C]+");

    /**
     * 全角ひらがな・カタカナのパターン。
     */
    static final Pattern FULLWIDTH_KANA_PATTERN =
            Pattern.compile("[　、。「」\u3041-\u3096\u3099-\u309E\u30A0-\u30FE]+");

    /**
     * 半角カナと互換性のある、全角ひらがな・カタカナのパターン。
     * <p>
     * 全角ひらがな・カタカナから変換に問題がある文字（ゝ、ゞ、ヸ、ヹ、ヽ、ヾ）を除いたもの。
     */
    static final Pattern FULLWIDTH_COMPATIBLE_KANA_PATTERN =
            Pattern.compile("[　、。「」\u3041-\u3096\u3099-\u309A\u30A0-\u30F7\u30FA-\u30FC]+");

    /**
     * 利用可能な文字のうち、JIS X 0213:2004 に含まれない文字。
     */
    static final String NON_JIS_2004 =
            // NEC 特殊文字。
            "\u2015∑"
            // IBM 拡張漢字 (補助漢字)。
            + "伹俍俿僘兤冾劜勀卲叝坥墲奓奣妺巐弡恝惞愑"
            + "戓朎昮汯浯涖渹猤玽珒珵琩硺羡菶蕫譓軏遧釞"
            + "鈆鉷鋕鎤鏸鐱鑈靃餧鮻"
            // IBM 拡張漢字。
            + "仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽"
            + "蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴";

    /**
     * 使用しないコンストラクタ。
     */
    private CharsetUtils() {
    }

    /**
     * 文字列を構成する文字がすべて半角数字か判定します。
     *
     * @param s 文字列
     * @return すべて半角数字ならば {@code true}
     */
    public static boolean isHalfwidthNumeric(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthNumeric);
    }

    /**
     * 指定されたコードポイントが半角数字か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角数字ならば {@code true}
     */
    static boolean isHalfwidthNumeric(int codePoint) {
        return ('0' <= codePoint) && (codePoint <= '9');
    }

    /**
     * 文字列を構成する文字がすべて半角英大文字か判定します。
     *
     * @param s 文字列
     * @return すべて半角英大文字ならば {@code true}
     */
    public static boolean isHalfwidthUpper(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthUpper);
    }

    /**
     * 指定されたコードポイントが半角英大文字か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角英大文字ならば {@code true}
     */
    static boolean isHalfwidthUpper(int codePoint) {
        return ('A' <= codePoint) && (codePoint <= 'Z');
    }

    /**
     * 文字列を構成する文字がすべて半角英小文字か判定します。
     *
     * @param s 文字列
     * @return すべて半角英小文字ならば {@code true}
     */
    public static boolean isHalfwidthLower(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthLower);
    }

    /**
     * 指定されたコードポイントが半角英小文字か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角英小文字ならば {@code true}
     */
    static boolean isHalfwidthLower(int codePoint) {
        return ('a' <= codePoint) && (codePoint <= 'z');
    }

    /**
     * 文字列を構成する文字がすべて半角英字か判定します。
     *
     * @param s 文字列
     * @return すべて半角英字ならば {@code true}
     */
    public static boolean isHalfwidthAlpha(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthAlpha);
    }

    /**
     * 指定されたコードポイントが半角英字か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角英字ならば {@code true}
     */
    static boolean isHalfwidthAlpha(int codePoint) {
        return isHalfwidthUpper(codePoint) || isHalfwidthLower(codePoint);
    }

    /**
     * 文字列を構成する文字がすべて半角英数字か判定します。
     *
     * @param s 文字列
     * @return すべて半角英数字ならば {@code true}
     */
    public static boolean isHalfwidthAlnum(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthAlnum);
    }

    /**
     * 指定されたコードポイントが半角英数字か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角英数字ならば {@code true}
     */
    static boolean isHalfwidthAlnum(int codePoint) {
        return isHalfwidthNumeric(codePoint) || isHalfwidthAlpha(codePoint);
    }

    /**
     * 文字列を構成する文字がすべて半角 ASCII か判定します。
     *
     * @param s 文字列
     * @return すべて半角 ASCII ならば {@code true}
     */
    public static boolean isHalfwidthAscii(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return s.chars().allMatch(CharsetUtils::isHalfwidthAscii);
    }

    /**
     * 指定されたコードポイントが半角 ASCII か判定します。
     *
     * @param codePoint コードポイント
     * @return 半角 ASCII ならば {@code true}
     */
    static boolean isHalfwidthAscii(int codePoint) {
        return (0x20 <= codePoint) && (codePoint <= 0x7E);
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
        //return HALFWIDTH_KATAKANA_PATTERN.matcher(s).matches();
        return s.chars().allMatch(CharsetUtils::isHalfwidthKatakana);
    }

    static boolean isHalfwidthKatakana(int codePoint) {
        return (0xFF65 <= codePoint) && (codePoint <= 0xFF9F);
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
        return s.chars().allMatch(CharsetUtils::isHalfwidthKana);
        //return HALFWIDTH_KANA_PATTERN.matcher(s).matches();
    }

    /**
     * 指定されたコードポイントが半角カナか判定します。
     *
     * @param codePoint コードポイント
     * @return 半角カナならば {@code true}
     */
    static boolean isHalfwidthKana(int codePoint) {
        return (0xFF61 <= codePoint) && (codePoint <= 0xFF9F);
    }

    /**
     * 文字列が半角カナを含まないことを判定します。
     *
     * @param s 文字列
     * @return 半角カナを含まなければ {@code true}
     */
    public static boolean noHalfwidthKana(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return !(s.chars().anyMatch(CharsetUtils::isHalfwidthKana));
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
        return ('ａ' <= codePoint) && (codePoint <= 'ｚ');
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
     * 文字列が全角英数字を含まないことを確認します。
     *
     * @param s 文字列
     * @return 全角英数字を含まなければ {@code true}
     */
    public static boolean noFullwidthAlnum(CharSequence s) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return !(s.chars().anyMatch(CharsetUtils::isFullwidthAlnum));
    }

    /**
     * 指定されたコードポイントが全角 ASCII か判定します。
     *
     * @param codePoint コードポイント
     * @return 全角 ASCII ならば {@code true}
     */
    static boolean isFullwidthAscii(int codePoint) {
        return ('\uFF01' <= codePoint) && (codePoint <= '\uFF5E');
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
     *   <li>JIS X 0208-1990 (全角 ASCII を除く)</li>
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
                && !isFullwidthAscii(codePoint)
                && codePoint != 0x2015
                && codePoint != 0x2225
                && !(0xFFE0 <= codePoint && codePoint <= 0xFFE2);
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
                CodeType.US_ASCII, CodeType.JIS_X_0201,
                CodeType.JIS_X_0208, CodeType.NEC_SPECIAL_CHAR, CodeType.IBM_EXT);
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
     * 文字列中の全角英数字を半角英数字に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toHalfwidthAlnum(CharSequence s) {
        return CodePointConverters.TO_HALFWIDTH_ALNUM.convert(s);
    }

    /**
     * 文字列中の全角 ASCII を半角 ASCII に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toHalfwidthAscii(CharSequence s) {
        return CodePointConverters.TO_HALFWIDTH_ASCII.convert(s);
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
        // 濁点・半濁点を分解する。
        String decomposed = StringUtils.replaceAll(s, FULLWIDTH_COMPATIBLE_KANA_PATTERN,
                g -> Normalizer.normalize(g, Normalizer.Form.NFD));
        // 半角カナに変換する。
        return CodePointConverters.TO_HALFWIDTH_KANA.convert(decomposed);
    }

    /**
     * 文字列中の半角英数字を全角英数字に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toFullwidthAlnum(CharSequence s) {
        return CodePointConverters.TO_FULLWIDTH_ALNUM.convert(s);
    }

    /**
     * 文字列中の半角カナを全角ひらがなに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toFullwidthHiragana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        // 全角カタカナに変換する。
        String converted = CodePointConverters.TO_FULLWIDTH_HIRAGANA.convert(s);
        // 濁点・半濁点を合成する。
        return StringUtils.replaceAll(converted, FULLWIDTH_KANA_PATTERN,
                g -> Normalizer.normalize(converted, Normalizer.Form.NFC));
    }

    /**
     * 文字列中の半角カナを全角カタカナに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toFullwidthKatakana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        // 全角カタカナに変換する。
        String converted = CodePointConverters.TO_FULLWIDTH_KATAKANA.convert(s);
        // 濁点・半濁点を合成する。
        return StringUtils.replaceAll(converted, FULLWIDTH_KANA_PATTERN,
                g -> Normalizer.normalize(converted, Normalizer.Form.NFC));
    }

    /**
     * 文字列中の全角カタカナを全角ひらがなに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toHiragana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return CodePointConverters.TO_HIRAGANA.convert(s);
    }

    /**
     * 文字列中の全角ひらがなを全角カタカナに変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toKatakana(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return CodePointConverters.TO_KATAKANA.convert(s);
    }

    /**
     * 文字列中の合成可能な濁点・半濁点を合成します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String composeVoicedSoundMark(CharSequence s) {
        if (s == null || "".equals(s)) {
            return (String) s;
        }
        return StringUtils.replaceAll(s, FULLWIDTH_KANA_PATTERN,
                g -> Normalizer.normalize(g, Normalizer.Form.NFC));
    }

    /**
     * 文字列を可能な範囲で JIS 1990 に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toJis1990(CharSequence s) {
        return CodePointConverters.TO_JIS_1990.convert(s);
    }

    /**
     * 文字列を可能な範囲で JIS 2004 に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toJis2004(CharSequence s) {
        return CodePointConverters.TO_JIS_2004.convert(s);
    }

    /**
     * 文字列を可能な範囲で Windows-31J に変換します。
     *
     * @param s 変換前の文字列
     * @return   変換後の文字列
     */
    public static String toWindows31j(CharSequence s) {
        return CodePointConverters.TO_WINDOWS_31J.convert(s);
    }

    /**
     * 文字列をバイト配列にエンコードします。
     *
     * @param s 文字列
     * @param charset 文字コード
     * @return バイト配列
     */
    public static byte[] encode(CharSequence s, Charset charset) {
        if (s == null) {
            return null;
        }
        CharBuffer in = CharBuffer.wrap(s);
        try {
            ByteBuffer out = charset.newEncoder().encode(in);
            byte[] bytes = new byte[out.remaining()];
            out.get(bytes);
            return bytes;
        } catch (CharacterCodingException e) {
            int position = in.position();
            if (in.hasRemaining()) {
                String rejected = String.valueOf(in.get());
                throw new CharacterCodingRuntimeException(position, rejected, e);
            } else {
                throw new CharacterCodingRuntimeException(position, (String) null, e);
            }
        }
    }

    /**
     * 文字列をバイト配列にエンコードします。
     *
     * @param s 文字列
     * @param charset 文字コード
     * @param replace 変換できない文字を置き換える文字
     * @return バイト配列
     */
    public static byte[] encode(CharSequence s, Charset charset, String replace) {
        if (s == null) {
            return null;
        }
        try {
            CharsetEncoder encoder = charset.newEncoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
            if (StringUtils.isNotEmpty(replace)) {
                encoder.replaceWith(replace.getBytes(charset));
            }
            ByteBuffer buf = encoder.encode(CharBuffer.wrap(s));
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            return bytes;
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
        if (bytes == null) {
            return null;
        }
        ByteBuffer in = ByteBuffer.wrap(bytes);
        try {
            return charset.newDecoder().decode(in).toString();
        } catch (CharacterCodingException e) {
            int position = in.position();
            if (in.hasRemaining()) {
                byte[] rejected = new byte[Math.min(2, in.remaining())];
                in.get(rejected);
                throw new CharacterCodingRuntimeException(position, rejected, e);
            } else {
                throw new CharacterCodingRuntimeException(position, (byte[]) null, e);
            }
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
        if (bytes == null) {
            return null;
        }
        try {
            CharsetDecoder decoder = charset.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);
            if (StringUtils.isNotEmpty(replace)) {
                decoder.replaceWith(replace);
            }
            return decoder
                .decode(ByteBuffer.wrap(bytes))
                .toString();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(e);
        }
    }

}
