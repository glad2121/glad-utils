package org.glad2121.charset;

/**
 * 様々なコードポイント変換表。
 *
 * @author glad2121
 */
final class CodePointConverters {

    /**
     * ASCII 数字。
     */
    static final String ASCII_NUMERICS = "0123456789";

    /**
     * ASCII 英大文字。
     */
    static final String ASCII_UPPERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * ASCII 英小文字。
     */
    static final String ASCII_LOWERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * ASCII 記号。
     */
    static final String ASCII_SYMBOLS = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}";

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
     * 全角数字。
     */
    static final String FULLWIDTH_NUMERICS = "０１２３４５６７８９";

    /**
     * 全角英大文字。
     */
    static final String FULLWIDTH_UPPERS =
            "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";

    /**
     * 全角英小文字。
     */
    static final String FULLWIDTH_LOWERS =
            "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";

    /**
     * 全角記号。
     */
    static final String FULLWIDTH_SYMBOLS =
            "！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［］＾＿｀｛｜｝";

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
     * 全角英数字を ASCII 英数字に変換するコンバータ。
     */
    static final CodePointConverter TO_ASCII_ALNUM = CodePointConverter.create(
            "TO_ASCII_ALNUM",
            FULLWIDTH_NUMERICS
            + FULLWIDTH_UPPERS
            + FULLWIDTH_LOWERS,
            ASCII_NUMERICS
            + ASCII_UPPERS
            + ASCII_LOWERS);

    /**
     * 全角英数記号を ASCII に変換するコンバータ。
     */
    static final CodePointConverter TO_ASCII = CodePointConverter.create(
            "TO_ASCII",
            "　"
            + FULLWIDTH_NUMERICS
            + FULLWIDTH_UPPERS
            + FULLWIDTH_LOWERS
            + FULLWIDTH_SYMBOLS,
            " "
            + ASCII_NUMERICS
            + ASCII_UPPERS
            + ASCII_LOWERS
            + ASCII_SYMBOLS);

    /**
     * 全角カタカナ・ひらがなを半角カナに変換するコンバータ。
     */
    static final CodePointConverter TO_HALFWIDTH_KANA = CodePointConverter.create(
            "TO_HALFWIDTH_KANA",
            '　'
            + FULLWIDTH_PUNCTATIONS
            + FULLWIDTH_KATAKANA
            + FULLWIDTH_HIRAGANA
            + '\u3099'  // 濁点（結合文字）。
            + '\u309A'  // 半濁点（結合文字）。
            + "゛゜",
            ' '
            + HALFWIDTH_PUNCTATIONS
            + HALFWIDTH_KATAKANA
            + HALFWIDTH_KATAKANA
            + "\uFF9E\uFF9F\uFF9E\uFF9F");

    /**
     * ASCII 英数字を全角英数字に変換するコンバータ。
     */
    static final CodePointConverter TO_FULLWIDTH_ALNUM = CodePointConverter.create(
            "TO_FULLWIDTH_ALNUM",
            ASCII_NUMERICS
            + ASCII_UPPERS
            + ASCII_LOWERS,
            FULLWIDTH_NUMERICS
            + FULLWIDTH_UPPERS
            + FULLWIDTH_LOWERS);

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
            + '\u3099'  // 濁点（結合文字）。
            + '\u309A'  // 半濁点（結合文字）。
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
            + '\u3099'  // 濁点（結合文字）。
            + '\u309A'  // 半濁点（結合文字）。
            + FULLWIDTH_HIRAGANA
            + "がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゎゐゑ\u3094\u3095\u3096ゝゞ");

    /**
     * 可能な範囲で JIS 1990 に変換するコンバータ。
     */
    static final CodePointConverter TO_JIS_1990 = CodePointConverter.create(
            "TO_JIS_1990",
            "\u00A5\u203E"
            // Windows-31J。
            + "\u2015\uFF5E\u2225\uFF0D\uFFE0\uFFE1\uFFE2∑＇＂"
            // IBM 拡張文字。
            + "悅晴淸益礼靖精羽逸閒靑飯飼館髙鶴"
            + "纊塚增寬德朗橫瀨猪甁神祥福綠緖薰諸賴郞都鄕隆黑"
            // JIS X 0213 第3水準。
            + "俱侮俠倂僧免勉勤卑卽啞喝嘆器嚙囊塡塀墨剝𠮟屢層屮吞噓巢廊徵悔慨憎懲戾揭搔摑擊攢敏"
            + "既晚暑曆梅槪欄步歷殺每海涉淚渚渴溫漢潑瀆焰煮狀琢硏碑社祉祈祐祖祝禍禎禱穀突節簞緣"
            + "練繁繡署者臭萊著蔣虛虜蟬蠟褐視謁謹賓贈軀逸醬醱錄鍊難響頰頻顚類驒鷗鹼麴麵黃姸屛幷瘦繫"
            // JIS X 0213 第4水準。
            + "縉",
            "￥￣"
            // Windows-31J → JIS X 0208。
            + "\u2014\u301C\u2016\u2212\u00A2\u00A3\u00ACΣ'\""
            // IBM 拡張文字 → JIS X 0208。
            + "悦晴清益礼靖精羽逸間青飯飼館高鶴"
            + "絋塚増寛徳朗横瀬猪瓶神祥福緑緒薫諸頼郎都郷隆黒"
            // JIS X 0213 第3水準 → JIS X 0208。
            + "倶侮侠併僧免勉勤卑即唖喝嘆器噛嚢填塀墨剥叱屡層屮呑嘘巣廊徴悔慨憎懲戻掲掻掴撃攅敏"
            + "既晩暑暦梅概欄歩歴殺毎海渉涙渚渇温漢溌涜焔煮状琢研碑社祉祈祐祖祝禍禎祷穀突節箪縁"
            + "練繁繍署者臭莱著蒋虚虜蝉蝋褐視謁謹賓贈躯逸醤醗録錬難響頬頻顛類騨鴎鹸麹麺黄妍屏并痩繋"
            // JIS X 0213 第4水準 → JIS X 0208。
            + "縉");

    /**
     * 可能な範囲で JIS 2004 に変換するコンバータ。
     */
    static final CodePointConverter TO_JIS_2004 = CodePointConverter.create(
            "TO_JIS_2004",
            "\u00A5\u203E"
            // Windows-31J。
            + "\u2015\uFFE0\uFFE1\uFFE2∑￤"
            // IBM 拡張文字。
            + "悅晴淸益礼靖精羽逸閒靑飯飼館髙鶴",
            "￥￣"
            // Windows-31J → JIS X 0213。
            + "\u2014\u00A2\u00A3\u00ACΣ¦"
            // IBM 拡張文字 → JIS X 0213。
            + "悦晴清益礼靖精羽逸間青飯飼館高鶴");

    /**
     * 可能な範囲で Windows-31J に変換するコンバータ。
     */
    static final CodePointConverter TO_WINDOWS_31J = CodePointConverter.create(
            "TO_WINDOWS_31J",
            "\u00A5\u203E"
            // JIS X 0208。
            + "\u2014\u301C\u2016\u2212\u00A2\u00A3\u00AC¦"
            // JIS X 0213 第3水準。
            + "俱侮俠倂僧免勉勤卑卽啞喝嘆器嚙囊塡塀墨剝𠮟屢層屮吞噓巢廊徵悔慨憎懲戾揭搔摑擊攢敏"
            + "既晚暑曆梅槪欄步歷殺每海涉淚渚渴溫漢潑瀆焰煮狀琢硏碑社祉祈祐祖祝禍禎禱穀突節簞緣"
            + "練繁繡署者臭萊著蔣虛虜蟬蠟褐視謁謹賓贈軀逸醬醱錄鍊難響頰頻顚類驒鷗鹼麴麵黃姸屛幷瘦繫"
            // JIS X 0213 第4水準。
            + "縉",
            "￥￣"
            // JIS X 0208 → Windows-31J。
            + "\u2015\uFF5E\u2225\uFF0D\uFFE0\uFFE1\uFFE2￤"
            // JIS X 0213 第3水準 → JIS X 0208。
            + "倶侮侠併僧免勉勤卑即唖喝嘆器噛嚢填塀墨剥叱屡層屮呑嘘巣廊徴悔慨憎懲戻掲掻掴撃攅敏"
            + "既晩暑暦梅概欄歩歴殺毎海渉涙渚渇温漢溌涜焔煮状琢研碑社祉祈祐祖祝禍禎祷穀突節箪縁"
            + "練繁繍署者臭莱著蒋虚虜蝉蝋褐視謁謹賓贈躯逸醤醗録錬難響頬頻顛類騨鴎鹸麹麺黄妍屏并痩繋"
            // JIS X 0213 第4水準 → JIS X 0208。
            + "縉");

    /**
     * 使用しないコンストラクタ。
     */
    private CodePointConverters() {
    }

}
