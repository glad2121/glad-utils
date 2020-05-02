package org.glad2121.charset;

import static org.assertj.core.api.Assertions.*;
import static org.glad2121.charset.CharsetUtils.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link CharsetUtils} の単体テスト。
 */
class CharsetUtilsTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("半角カタカナの判定が正しく行われること。")
    void testIsHalfwidthKatakana() {
        assertThat(isHalfwidthKatakana(null)).isTrue();
        assertThat(isHalfwidthKatakana("")).isTrue();
        assertThat(isHalfwidthKatakana(" ")).isFalse();

        // 半角英数字
        assertThat(isHalfwidthKatakana("0123456789")).isFalse();
        assertThat(isHalfwidthKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isHalfwidthKatakana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ
        assertThat(isHalfwidthKatakana("､｡｢｣")).isFalse();
        assertThat(isHalfwidthKatakana("ｧｨｩｪｫｬｭｮｯ･ｰ")).isTrue();
        assertThat(isHalfwidthKatakana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();
        assertThat(isHalfwidthKatakana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue();
        assertThat(isHalfwidthKatakana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ")).isTrue();

        // 全角カタカナ
        assertThat(isHalfwidthKatakana("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();

        // 全角ひらがな。
        assertThat(isHalfwidthKatakana("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();

        // 漢字。
        assertThat(isHalfwidthKatakana("漢字")).isFalse();
    }

    @Test
    @DisplayName("半角カナの判定が正しく行われること。")
    void testIsHalfwidthKana() {
        assertThat(isHalfwidthKana(null)).isTrue();
        assertThat(isHalfwidthKana("")).isTrue();
        assertThat(isHalfwidthKana(" ")).isFalse();

        // 半角英数字
        assertThat(isHalfwidthKana("0123456789")).isFalse();
        assertThat(isHalfwidthKana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isHalfwidthKana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ
        assertThat(isHalfwidthKana("､｡｢｣")).isTrue();
        assertThat(isHalfwidthKana("ｧｨｩｪｫｬｭｮｯ･ｰ")).isTrue();
        assertThat(isHalfwidthKana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();
        assertThat(isHalfwidthKana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue();
        assertThat(isHalfwidthKana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ")).isTrue();

        // 全角カタカナ
        assertThat(isHalfwidthKana("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();

        // 全角ひらがな。
        assertThat(isHalfwidthKana("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();

        // 漢字。
        assertThat(isHalfwidthKana("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角カタカナの判定が正しく行われること。")
    void testIsFullwidthKatakana() {
        assertThat(isFullwidthKatakana(null)).isTrue();
        assertThat(isFullwidthKatakana("")).isTrue();
        assertThat(isFullwidthKatakana(" ")).isFalse();

        // 半角英数字
        assertThat(isFullwidthKatakana("0123456789")).isFalse();
        assertThat(isFullwidthKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthKatakana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ
        assertThat(isFullwidthKatakana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角カタカナ
        assertThat(isFullwidthKatakana("　")).isFalse();
        assertThat(isFullwidthKatakana("ァィゥェォャュョッヵヶ・ー")).isTrue();
        assertThat(isFullwidthKatakana("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();
        assertThat(isFullwidthKatakana("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲン")).isTrue();
        assertThat(isFullwidthKatakana("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴヽヾ")).isTrue();

        // 全角ひらがな。
        assertThat(isFullwidthKatakana("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();

        // 漢字。
        assertThat(isFullwidthKatakana("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角ひらがなの判定が正しく行われること。")
    void testIsFullwidthHiragana() {
        assertThat(isFullwidthHiragana(null)).isTrue();
        assertThat(isFullwidthHiragana("")).isTrue();
        assertThat(isFullwidthHiragana(" ")).isFalse();

        // 半角英数字
        assertThat(isFullwidthHiragana("0123456789")).isFalse();
        assertThat(isFullwidthHiragana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthHiragana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ
        assertThat(isFullwidthHiragana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角カタカナ
        assertThat(isFullwidthHiragana("　")).isFalse();
        assertThat(isFullwidthHiragana("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();

        // 全角ひらがな。
        assertThat(isFullwidthHiragana("ぁぃぅぇぉゃゅょっ・ー")).isTrue();
        assertThat(isFullwidthHiragana("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();
        assertThat(isFullwidthHiragana("はひふへほまみむめもやゆよらりるれろわゐゑをん")).isTrue();
        assertThat(isFullwidthHiragana("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゝゞ")).isTrue();

        // 漢字。
        assertThat(isFullwidthHiragana("漢字")).isFalse();
    }

    @Test
    @DisplayName("利用可能文字の判定が正しく行われること。")
    void testIsAvailable() {
        assertThat(isAvailable(null)).isTrue();
        assertThat(isAvailable("")).isTrue();
        assertThat(isAvailable(" ")).isTrue();

        // 半角英数字
        assertThat(isAvailable("0123456789")).isTrue();
        assertThat(isAvailable("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAvailable("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ
        assertThat(isAvailable("､｡｢｣･ｰ")).isTrue();
        assertThat(isAvailable("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();

        // 全角英数字
        assertThat(isAvailable("　")).isTrue();
        assertThat(isAvailable("０１２３４５６７８９")).isTrue();
        assertThat(isAvailable("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isAvailable("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ
        assertThat(isAvailable("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな
        assertThat(isAvailable("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();

        // 第1水準漢字。
        assertThat(isAvailable("亜唖娃阿哀愛挨姶逢葵茜穐悪握渥旭葦芦鯵梓")).isTrue();

        // 第2水準漢字。
        assertThat(isAvailable("弌丐丕个丱丶丼丿乂乖乘亂亅豫亊舒弍于亞亟")).isTrue();

        // NEC特殊文字。
        assertThat(isAvailable("①②③④⑤⑥⑦⑧⑨⑩ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ")).isTrue();

        // IBM拡張漢字。
        assertThat(isAvailable("仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽")).isTrue();
        assertThat(isAvailable("蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴")).isTrue();

        // 第3水準漢字。
        assertThat(isAvailable("俱𠀋㐂丯丰亍份仿伋你佈佉佟佪佬佾侗侮俠倁")).isTrue();

        // 第4水準漢字。
        assertThat(isAvailable("𠂉丂丏丒丩丫丮乀乇么𠂢乑㐆𠂤乚乩亝㐬㐮亹")).isTrue();
    }

    @Test
    @DisplayName("半角カナへ正しく変換されること。")
    void testToHalfwidthKana() {
        assertThat(toHalfwidthKana(null)).isNull();
        assertThat(toHalfwidthKana("")).isEqualTo("");
        assertThat(toHalfwidthKana(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toHalfwidthKana("0123456789"))
            .isEqualTo("0123456789");
        assertThat(toHalfwidthKana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toHalfwidthKana("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");

        // 半角カナ。
        assertThat(toHalfwidthKana("､｡｢｣･ｰ")).isEqualTo("､｡｢｣･ｰ");
        assertThat(toHalfwidthKana("ｧｨｩｪｫｬｭｮｯ")).isEqualTo("ｧｨｩｪｫｬｭｮｯ");
        assertThat(toHalfwidthKana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");
        assertThat(toHalfwidthKana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ"))
            .isEqualTo("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ");

        // 全角句読点。
        assertThat(toHalfwidthKana("　、。「」・ー")).isEqualTo(" ､｡｢｣･ｰ");

        // 全角カタカナ。
        assertThat(toHalfwidthKana("ァィゥェォャュョッヮヵヶ")).isEqualTo("ｧｨｩｪｫｬｭｮｯヮヵヶ");
        assertThat(toHalfwidthKana("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");
        assertThat(toHalfwidthKana("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲンヽ"))
            .isEqualTo("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜヰヱｦﾝヽ");
        assertThat(toHalfwidthKana("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴヾ"))
            .isEqualTo("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞヾ");

        // 全角ひらがな。
        assertThat(toHalfwidthKana("ぁぃぅぇぉゃゅょっゎ")).isEqualTo("ｧｨｩｪｫｬｭｮｯゎ");
        assertThat(toHalfwidthKana("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");
        assertThat(toHalfwidthKana("はひふへほまみむめもやゆよらりるれろわゐゑをんゝ"))
            .isEqualTo("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜゐゑｦﾝゝ");
        assertThat(toHalfwidthKana("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゞ"))
            .isEqualTo("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟゞ");

        // 漢字。
        assertThat(toHalfwidthKana("漢字")).isEqualTo("漢字");
    }

    @Test
    @DisplayName("全角カタカナへ正しく変換されること。")
    void testToFullwidthKatakana() {
        assertThat(toFullwidthKatakana(null)).isNull();
        assertThat(toFullwidthKatakana("")).isEqualTo("");
        assertThat(toFullwidthKatakana(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toFullwidthKatakana("0123456789"))
            .isEqualTo("0123456789");
        assertThat(toFullwidthKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toFullwidthKatakana("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");

        // 半角カナ。
        assertThat(toFullwidthKatakana("､｡｢｣･ｰ")).isEqualTo("、。「」・ー");
        assertThat(toFullwidthKatakana("ｧｨｩｪｫｬｭｮｯ"))
            .isEqualTo("ァィゥェォャュョッ");
        assertThat(toFullwidthKatakana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toFullwidthKatakana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ"))
            .isEqualTo("ハヒフヘホマミムメモヤユヨラリルレロワヲン");
        assertThat(toFullwidthKatakana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ"))
            .isEqualTo("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴ");

        // 全角句読点。
        assertThat(toFullwidthKatakana("　、。「」・ー")).isEqualTo("　、。「」・ー");

        // 全角カタカナ。
        assertThat(toFullwidthKatakana("ァィゥェォャュョッヮヵヶ"))
            .isEqualTo("ァィゥェォャュョッヮヵヶ");
        assertThat(toFullwidthKatakana("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toFullwidthKatakana("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲンヽ"))
            .isEqualTo("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲンヽ");
        assertThat(toFullwidthKatakana("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴヾ"))
            .isEqualTo("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴヾ");

        // 全角ひらがな。
        assertThat(toFullwidthKatakana("ぁぃぅぇぉゃゅょっゎ"))
            .isEqualTo("ァィゥェォャュョッヮ");
        assertThat(toFullwidthKatakana("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toFullwidthKatakana("はひふへほまみむめもやゆよらりるれろわゐゑをんゝ"))
            .isEqualTo("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲンヽ");
        assertThat(toFullwidthKatakana("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゞ"))
            .isEqualTo("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヾ");

        // 漢字。
        assertThat(toFullwidthKatakana("漢字")).isEqualTo("漢字");
    }

    @Test
    @DisplayName("全角ひらがなへ正しく変換されること。")
    void testToFullwidthHiragana() {
        assertThat(toFullwidthHiragana(null)).isNull();
        assertThat(toFullwidthHiragana("")).isEqualTo("");
        assertThat(toFullwidthHiragana(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toFullwidthHiragana("0123456789"))
            .isEqualTo("0123456789");
        assertThat(toFullwidthHiragana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toFullwidthHiragana("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");

        // 半角カナ。
        assertThat(toFullwidthHiragana("､｡｢｣･ｰ")).isEqualTo("、。「」・ー");
        assertThat(toFullwidthHiragana("ｧｨｩｪｫｬｭｮｯ"))
            .isEqualTo("ぁぃぅぇぉゃゅょっ");
        assertThat(toFullwidthHiragana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toFullwidthHiragana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ"))
            .isEqualTo("はひふへほまみむめもやゆよらりるれろわをん");
        assertThat(toFullwidthHiragana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ"))
            .isEqualTo("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽ\u3094");

        // 全角句読点。
        assertThat(toFullwidthHiragana("　、。「」・ー")).isEqualTo("　、。「」・ー");

        // 全角カタカナ。
        assertThat(toFullwidthHiragana("ァィゥェォャュョッヮヵヶ"))
            .isEqualTo("ぁぃぅぇぉゃゅょっゎ\u3095\u3096");
        assertThat(toFullwidthHiragana("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toFullwidthHiragana("ハヒフヘホマミムメモヤユヨラリルレロワヰヱヲンヽ"))
            .isEqualTo("はひふへほまみむめもやゆよらりるれろわゐゑをんゝ");
        assertThat(toFullwidthHiragana("ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポヴヾ"))
            .isEqualTo("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽ\u3094ゞ");

        // 全角ひらがな。
        assertThat(toFullwidthHiragana("ぁぃぅぇぉゃゅょっゎ"))
            .isEqualTo("ぁぃぅぇぉゃゅょっゎ");
        assertThat(toFullwidthHiragana("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toFullwidthHiragana("はひふへほまみむめもやゆよらりるれろわゐゑをんゝ"))
            .isEqualTo("はひふへほまみむめもやゆよらりるれろわゐゑをんゝ");
        assertThat(toFullwidthHiragana("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゞ"))
            .isEqualTo("がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽゞ");

        // 漢字。
        assertThat(toFullwidthHiragana("漢字")).isEqualTo("漢字");
    }

}
