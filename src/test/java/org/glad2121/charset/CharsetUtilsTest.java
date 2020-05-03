package org.glad2121.charset;

import static org.assertj.core.api.Assertions.*;
import static org.glad2121.charset.CharsetUtils.*;

import org.glad2121.util.ArrayUtils;
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
    @DisplayName("ASCII 数字の判定が正しく行われること。")
    void testAsciiNumeric() {
        assertThat(isAsciiNumeric(null)).isTrue();
        assertThat(isAsciiNumeric("")).isTrue();
        assertThat(isAsciiNumeric(" ")).isFalse();

        // 半角英数字。
        assertThat(isAsciiNumeric("0123456789")).isTrue();
        assertThat(isAsciiNumeric("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isAsciiNumeric("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isAsciiNumeric("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAsciiNumeric("　")).isFalse();
        assertThat(isAsciiNumeric("０１２３４５６７８９")).isFalse();
        assertThat(isAsciiNumeric("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAsciiNumeric("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAsciiNumeric("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAsciiNumeric("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAsciiNumeric("漢字")).isFalse();
}

    @Test
    @DisplayName("ASCII 英大文字の判定が正しく行われること。")
    void testAsciiUpper() {
        assertThat(isAsciiUpper(null)).isTrue();
        assertThat(isAsciiUpper("")).isTrue();
        assertThat(isAsciiUpper(" ")).isFalse();

        // 半角英数字。
        assertThat(isAsciiUpper("0123456789")).isFalse();
        assertThat(isAsciiUpper("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAsciiUpper("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isAsciiUpper("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAsciiUpper("　")).isFalse();
        assertThat(isAsciiUpper("０１２３４５６７８９")).isFalse();
        assertThat(isAsciiUpper("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAsciiUpper("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAsciiUpper("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAsciiUpper("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAsciiUpper("漢字")).isFalse();
    }

    @Test
    @DisplayName("ASCII 英小文字の判定が正しく行われること。")
    void testAsciiLower() {
        assertThat(isAsciiLower(null)).isTrue();
        assertThat(isAsciiLower("")).isTrue();
        assertThat(isAsciiLower(" ")).isFalse();

        // 半角英数字。
        assertThat(isAsciiLower("0123456789")).isFalse();
        assertThat(isAsciiLower("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isAsciiLower("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isAsciiLower("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAsciiLower("　")).isFalse();
        assertThat(isAsciiLower("０１２３４５６７８９")).isFalse();
        assertThat(isAsciiLower("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAsciiLower("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAsciiLower("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAsciiLower("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAsciiLower("漢字")).isFalse();
    }

    @Test
    @DisplayName("ASCII 英字の判定が正しく行われること。")
    void testAsciiAlpha() {
        assertThat(isAsciiAlpha(null)).isTrue();
        assertThat(isAsciiAlpha("")).isTrue();
        assertThat(isAsciiAlpha(" ")).isFalse();

        // 半角英数字。
        assertThat(isAsciiAlpha("0123456789")).isFalse();
        assertThat(isAsciiAlpha("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAsciiAlpha("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isAsciiAlpha("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAsciiAlpha("　")).isFalse();
        assertThat(isAsciiAlpha("０１２３４５６７８９")).isFalse();
        assertThat(isAsciiAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAsciiAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAsciiAlpha("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAsciiAlpha("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAsciiAlpha("漢字")).isFalse();
    }

    @Test
    @DisplayName("ASCII 英数字の判定が正しく行われること。")
    void testAsciiAlnum() {
        assertThat(isAsciiAlnum(null)).isTrue();
        assertThat(isAsciiAlnum("")).isTrue();
        assertThat(isAsciiAlnum(" ")).isFalse();

        // 半角英数字。
        assertThat(isAsciiAlnum("0123456789")).isTrue();
        assertThat(isAsciiAlnum("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAsciiAlnum("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isAsciiAlnum("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAsciiAlnum("　")).isFalse();
        assertThat(isAsciiAlnum("０１２３４５６７８９")).isFalse();
        assertThat(isAsciiAlnum("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAsciiAlnum("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAsciiAlnum("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAsciiAlnum("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAsciiAlnum("漢字")).isFalse();
    }

    @Test
    @DisplayName("ASCII の判定が正しく行われること。")
    void testAscii() {
        assertThat(isAscii(null)).isTrue();
        assertThat(isAscii("")).isTrue();
        assertThat(isAscii(" ")).isTrue();

        // 半角英数字。
        assertThat(isAscii("0123456789")).isTrue();
        assertThat(isAscii("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAscii("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isAscii("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isAscii("　")).isFalse();
        assertThat(isAscii("０１２３４５６７８９")).isFalse();
        assertThat(isAscii("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isAscii("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isAscii("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isAscii("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isAscii("漢字")).isFalse();
    }

    @Test
    @DisplayName("半角カタカナの判定が正しく行われること。")
    void testIsHalfwidthKatakana() {
        assertThat(isHalfwidthKatakana(null)).isTrue();
        assertThat(isHalfwidthKatakana("")).isTrue();
        assertThat(isHalfwidthKatakana(" ")).isFalse();

        // 半角英数字。
        assertThat(isHalfwidthKatakana("0123456789")).isFalse();
        assertThat(isHalfwidthKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isHalfwidthKatakana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isHalfwidthKatakana("､｡｢｣")).isFalse();
        assertThat(isHalfwidthKatakana("ｧｨｩｪｫｬｭｮｯ･ｰ")).isTrue();
        assertThat(isHalfwidthKatakana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();
        assertThat(isHalfwidthKatakana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue();
        assertThat(isHalfwidthKatakana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ")).isTrue();

        // 全角英数字。
        assertThat(isHalfwidthKatakana("　")).isFalse();
        assertThat(isHalfwidthKatakana("０１２３４５６７８９")).isFalse();
        assertThat(isHalfwidthKatakana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isHalfwidthKatakana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
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

        // 半角英数字。
        assertThat(isHalfwidthKana("0123456789")).isFalse();
        assertThat(isHalfwidthKana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isHalfwidthKana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isHalfwidthKana("､｡｢｣")).isTrue();
        assertThat(isHalfwidthKana("ｧｨｩｪｫｬｭｮｯ･ｰ")).isTrue();
        assertThat(isHalfwidthKana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();
        assertThat(isHalfwidthKana("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue();
        assertThat(isHalfwidthKana("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ")).isTrue();

        // 全角英数字。
        assertThat(isHalfwidthKana("　")).isFalse();
        assertThat(isHalfwidthKana("０１２３４５６７８９")).isFalse();
        assertThat(isHalfwidthKana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isHalfwidthKana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
        assertThat(isHalfwidthKana("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();

        // 全角ひらがな。
        assertThat(isHalfwidthKana("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();

        // 漢字。
        assertThat(isHalfwidthKana("漢字")).isFalse();
    }

    @Test
    @DisplayName("JIS X 0201 の判定が正しく行われること。")
    void testIsJisX0201() {
        assertThat(isJisX0201(null)).isTrue();
        assertThat(isJisX0201("")).isTrue();
        assertThat(isJisX0201(" ")).isTrue();

        // 半角英数字。
        assertThat(isJisX0201("0123456789")).isTrue();
        assertThat(isJisX0201("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isJisX0201("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isJisX0201("､｡｢｣")).isTrue();
        assertThat(isJisX0201("ｧｨｩｪｫｬｭｮｯ･ｰ")).isTrue();
        assertThat(isJisX0201("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();
        assertThat(isJisX0201("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue();
        assertThat(isJisX0201("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟｳﾞ")).isTrue();

        // 全角英数字。
        assertThat(isJisX0201("　")).isFalse();
        assertThat(isJisX0201("０１２３４５６７８９")).isFalse();
        assertThat(isJisX0201("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isJisX0201("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
        assertThat(isJisX0201("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();

        // 全角ひらがな。
        assertThat(isJisX0201("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();

        // 漢字。
        assertThat(isHalfwidthKana("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角数字の判定が正しく行われること。")
    void testFullwidthNumeric() {
        assertThat(isFullwidthNumeric(null)).isTrue();
        assertThat(isFullwidthNumeric("")).isTrue();
        assertThat(isFullwidthNumeric(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthNumeric("0123456789")).isFalse();
        assertThat(isFullwidthNumeric("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthNumeric("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthNumeric("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthNumeric("　")).isFalse();
        assertThat(isFullwidthNumeric("０１２３４５６７８９")).isTrue();
        assertThat(isFullwidthNumeric("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isFullwidthNumeric("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isFullwidthNumeric("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isFullwidthNumeric("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isFullwidthNumeric("漢字")).isFalse();
}

    @Test
    @DisplayName("全角英大文字の判定が正しく行われること。")
    void testFullwidthUpper() {
        assertThat(isFullwidthUpper(null)).isTrue();
        assertThat(isFullwidthUpper("")).isTrue();
        assertThat(isFullwidthUpper(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthUpper("0123456789")).isFalse();
        assertThat(isFullwidthUpper("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthUpper("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthUpper("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthUpper("　")).isFalse();
        assertThat(isFullwidthUpper("０１２３４５６７８９")).isFalse();
        assertThat(isFullwidthUpper("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isFullwidthUpper("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isFullwidthUpper("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isFullwidthUpper("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isFullwidthUpper("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角英小文字の判定が正しく行われること。")
    void testFullwidthLower() {
        assertThat(isFullwidthLower(null)).isTrue();
        assertThat(isFullwidthLower("")).isTrue();
        assertThat(isFullwidthLower(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthLower("0123456789")).isFalse();
        assertThat(isFullwidthLower("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthLower("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthLower("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthLower("　")).isFalse();
        assertThat(isFullwidthLower("０１２３４５６７８９")).isFalse();
        assertThat(isFullwidthLower("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isFullwidthLower("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isFullwidthLower("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isFullwidthLower("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isFullwidthLower("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角英字の判定が正しく行われること。")
    void testFullwidthAlpha() {
        assertThat(isFullwidthAlpha(null)).isTrue();
        assertThat(isFullwidthAlpha("")).isTrue();
        assertThat(isFullwidthAlpha(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthAlpha("0123456789")).isFalse();
        assertThat(isFullwidthAlpha("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthAlpha("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthAlpha("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthAlpha("　")).isFalse();
        assertThat(isFullwidthAlpha("０１２３４５６７８９")).isFalse();
        assertThat(isFullwidthAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isFullwidthAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isFullwidthAlpha("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isFullwidthAlpha("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isFullwidthAlpha("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角英数字の判定が正しく行われること。")
    void testFullwidthAlnum() {
        assertThat(isFullwidthAlnum(null)).isTrue();
        assertThat(isFullwidthAlnum("")).isTrue();
        assertThat(isFullwidthAlnum(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthAlnum("0123456789")).isFalse();
        assertThat(isFullwidthAlnum("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthAlnum("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthAlnum("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthAlnum("　")).isFalse();
        assertThat(isFullwidthAlnum("０１２３４５６７８９")).isTrue();
        assertThat(isFullwidthAlnum("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isFullwidthAlnum("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ、ひらがな、漢字。
        assertThat(isFullwidthAlnum("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isFalse();
        assertThat(isFullwidthAlnum("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isFalse();
        assertThat(isFullwidthAlnum("漢字")).isFalse();
    }

    @Test
    @DisplayName("全角カタカナの判定が正しく行われること。")
    void testIsFullwidthKatakana() {
        assertThat(isFullwidthKatakana(null)).isTrue();
        assertThat(isFullwidthKatakana("")).isTrue();
        assertThat(isFullwidthKatakana(" ")).isFalse();

        // 半角英数字。
        assertThat(isFullwidthKatakana("0123456789")).isFalse();
        assertThat(isFullwidthKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthKatakana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthKatakana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthKatakana("　")).isFalse();
        assertThat(isFullwidthKatakana("０１２３４５６７８９")).isFalse();
        assertThat(isFullwidthKatakana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isFullwidthKatakana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
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

        // 半角英数字。
        assertThat(isFullwidthHiragana("0123456789")).isFalse();
        assertThat(isFullwidthHiragana("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isFalse();
        assertThat(isFullwidthHiragana("abcdefghijklmnopqrstuvwxyz")).isFalse();

        // 半角カナ。
        assertThat(isFullwidthHiragana("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isFullwidthHiragana("　")).isFalse();
        assertThat(isFullwidthHiragana("０１２３４５６７８９")).isFalse();
        assertThat(isFullwidthHiragana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isFullwidthHiragana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
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
    @DisplayName("JIS 1990 の判定が正しく行われること。")
    void testIsJis1990() {
        assertThat(isJis1990(null)).isTrue();
        assertThat(isJis1990("")).isTrue();
        assertThat(isJis1990(" ")).isTrue();

        // 半角英数字。
        assertThat(isJis1990("0123456789")).isTrue();
        assertThat(isJis1990("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isJis1990("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isJis1990("､｡｢｣･ｰ")).isTrue();
        assertThat(isJis1990("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();

        // 全角英数字。
        assertThat(isJis1990("　")).isTrue();
        assertThat(isJis1990("０１２３４５６７８９")).isTrue();
        assertThat(isJis1990("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isJis1990("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ。
        assertThat(isJis1990("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな。
        assertThat(isJis1990("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();

        // 第1水準漢字。
        assertThat(isJis1990("亜唖娃阿哀愛挨姶逢葵茜穐悪握渥旭葦芦鯵梓")).isTrue();

        // 第2水準漢字。
        assertThat(isJis1990("弌丐丕个丱丶丼丿乂乖乘亂亅豫亊舒弍于亞亟")).isTrue();

        // NEC特殊文字。
        assertThat(isJis1990("①②③④⑤⑥⑦⑧⑨⑩ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ")).isFalse();

        // IBM拡張漢字。
        assertThat(isJis1990("仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽")).isFalse();
        assertThat(isJis1990("蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴")).isFalse();

        // 第3水準漢字。
        assertThat(isJis1990("俱𠀋㐂丯丰亍份仿伋你佈佉佟佪佬佾侗侮俠倁")).isFalse();

        // 第4水準漢字。
        assertThat(isJis1990("𠂉丂丏丒丩丫丮乀乇么𠂢乑㐆𠂤乚乩亝㐬㐮亹")).isFalse();
    }

    @Test
    @DisplayName("JIS 2004 の判定が正しく行われること。")
    void testIsJis2004() {
        assertThat(isJis2004(null)).isTrue();
        assertThat(isJis2004("")).isTrue();
        assertThat(isJis2004(" ")).isTrue();

        // 半角英数字。
        assertThat(isJis2004("0123456789")).isTrue();
        assertThat(isJis2004("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isJis2004("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isJis2004("､｡｢｣･ｰ")).isTrue();
        assertThat(isJis2004("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();

        // 全角英数字。
        assertThat(isJis2004("　")).isTrue();
        assertThat(isJis2004("０１２３４５６７８９")).isTrue();
        assertThat(isJis2004("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isJis2004("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ。
        assertThat(isJis2004("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな。
        assertThat(isJis2004("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();

        // 第1水準漢字。
        assertThat(isJis2004("亜唖娃阿哀愛挨姶逢葵茜穐悪握渥旭葦芦鯵梓")).isTrue();

        // 第2水準漢字。
        assertThat(isJis2004("弌丐丕个丱丶丼丿乂乖乘亂亅豫亊舒弍于亞亟")).isTrue();

        // NEC特殊文字。
        assertThat(isJis2004("①②③④⑤⑥⑦⑧⑨⑩ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ")).isTrue();

        // IBM拡張漢字。
        assertThat(isJis2004("仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽")).isFalse();
        assertThat(isJis2004("蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴")).isFalse();

        // 第3水準漢字。
        assertThat(isJis2004("俱𠀋㐂丯丰亍份仿伋你佈佉佟佪佬佾侗侮俠倁")).isTrue();

        // 第4水準漢字。
        assertThat(isJis2004("𠂉丂丏丒丩丫丮乀乇么𠂢乑㐆𠂤乚乩亝㐬㐮亹")).isTrue();
    }

    @Test
    @DisplayName("基本日本文字集合の判定が正しく行われること。")
    void testIsBasicJ() {
        assertThat(isBasicJ(null)).isTrue();
        assertThat(isBasicJ("")).isTrue();
        assertThat(isBasicJ(" ")).isTrue();

        // 半角英数字。
        assertThat(isBasicJ("0123456789")).isTrue();
        assertThat(isBasicJ("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isBasicJ("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isBasicJ("､｡｢｣･ｰ")).isFalse();
        assertThat(isBasicJ("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isFalse();

        // 全角英数字。
        assertThat(isBasicJ("　")).isTrue();
        assertThat(isBasicJ("０１２３４５６７８９")).isFalse();
        assertThat(isBasicJ("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isFalse();
        assertThat(isBasicJ("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isFalse();

        // 全角カタカナ。
        assertThat(isBasicJ("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな。
        assertThat(isBasicJ("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();

        // 第1水準漢字。
        assertThat(isBasicJ("亜唖娃阿哀愛挨姶逢葵茜穐悪握渥旭葦芦鯵梓")).isTrue();

        // 第2水準漢字。
        assertThat(isBasicJ("弌丐丕个丱丶丼丿乂乖乘亂亅豫亊舒弍于亞亟")).isTrue();

        // NEC特殊文字。
        assertThat(isBasicJ("①②③④⑤⑥⑦⑧⑨⑩ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ")).isFalse();

        // IBM拡張漢字。
        assertThat(isBasicJ("仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽")).isFalse();
        assertThat(isBasicJ("蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴")).isFalse();

        // 第3水準漢字。
        assertThat(isBasicJ("俱𠀋㐂丯丰亍份仿伋你佈佉佟佪佬佾侗侮俠倁")).isFalse();

        // 第4水準漢字。
        assertThat(isBasicJ("𠂉丂丏丒丩丫丮乀乇么𠂢乑㐆𠂤乚乩亝㐬㐮亹")).isFalse();
    }

    @Test
    @DisplayName("通用日本文字集合の判定が正しく行われること。")
    void testIsCommonJ() {
        assertThat(isCommonJ(null)).isTrue();
        assertThat(isCommonJ("")).isTrue();
        assertThat(isCommonJ(" ")).isTrue();

        // 半角英数字。
        assertThat(isCommonJ("0123456789")).isTrue();
        assertThat(isCommonJ("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isCommonJ("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isCommonJ("､｡｢｣･ｰ")).isTrue();
        assertThat(isCommonJ("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();

        // 全角英数字。
        assertThat(isCommonJ("　")).isTrue();
        assertThat(isCommonJ("０１２３４５６７８９")).isTrue();
        assertThat(isCommonJ("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isCommonJ("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ。
        assertThat(isCommonJ("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな。
        assertThat(isCommonJ("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue();

        // 第1水準漢字。
        assertThat(isCommonJ("亜唖娃阿哀愛挨姶逢葵茜穐悪握渥旭葦芦鯵梓")).isTrue();

        // 第2水準漢字。
        assertThat(isCommonJ("弌丐丕个丱丶丼丿乂乖乘亂亅豫亊舒弍于亞亟")).isTrue();

        // NEC特殊文字。
        assertThat(isCommonJ("①②③④⑤⑥⑦⑧⑨⑩ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ")).isTrue();

        // IBM拡張漢字。
        assertThat(isCommonJ("仼僴凬﨎坙峵悅愠敎昻晴櫢淸淲皂益礼靖精羽")).isTrue();
        assertThat(isCommonJ("蠇赶﨣逸﨧﨨閒﨩靑飯飼館髙鶴")).isTrue();

        // 第3水準漢字。
        assertThat(isCommonJ("俱𠀋㐂丯丰亍份仿伋你佈佉佟佪佬佾侗侮俠倁")).isFalse();

        // 第4水準漢字。
        assertThat(isCommonJ("𠂉丂丏丒丩丫丮乀乇么𠂢乑㐆𠂤乚乩亝㐬㐮亹")).isFalse();
    }

    @Test
    @DisplayName("利用可能文字の判定が正しく行われること。")
    void testIsAvailable() {
        assertThat(isAvailable(null)).isTrue();
        assertThat(isAvailable("")).isTrue();
        assertThat(isAvailable(" ")).isTrue();

        // 半角英数字。
        assertThat(isAvailable("0123456789")).isTrue();
        assertThat(isAvailable("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
        assertThat(isAvailable("abcdefghijklmnopqrstuvwxyz")).isTrue();

        // 半角カナ。
        assertThat(isAvailable("､｡｢｣･ｰ")).isTrue();
        assertThat(isAvailable("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue();

        // 全角英数字。
        assertThat(isAvailable("　")).isTrue();
        assertThat(isAvailable("０１２３４５６７８９")).isTrue();
        assertThat(isAvailable("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue();
        assertThat(isAvailable("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue();

        // 全角カタカナ。
        assertThat(isAvailable("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue();

        // 全角ひらがな。
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
    @DisplayName("ASCII 英数字へ正しく変換されること。")
    void testToAsciiAlnum() {
        assertThat(toAsciiAlnum(null)).isNull();
        assertThat(toAsciiAlnum("")).isEqualTo("");
        assertThat(toAsciiAlnum(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toAsciiAlnum("0123456789"))
            .isEqualTo("0123456789");
        assertThat(toAsciiAlnum("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toAsciiAlnum("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");
        assertThat(toAsciiAlnum("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"))
            .isEqualTo("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

        // 半角カナ。
        assertThat(toAsciiAlnum("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");

        // 全角英数字。
        assertThat(toAsciiAlnum("　")).isEqualTo("　");
        assertThat(toAsciiAlnum("０１２３４５６７８９"))
            .isEqualTo("0123456789");
        assertThat(toAsciiAlnum("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toAsciiAlnum("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");
        assertThat(toAsciiAlnum("！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝〜￥"))
            .isEqualTo("！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝〜￥");

        // 全角カタカナ、ひらがな、漢字。
        assertThat(toAsciiAlnum("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toAsciiAlnum("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toAsciiAlnum("漢字")).isEqualTo("漢字");
    }

    @Test
    @DisplayName("ASCII へ正しく変換されること。")
    void testToAscii() {
        assertThat(toAscii(null)).isNull();
        assertThat(toAscii("")).isEqualTo("");
        assertThat(toAscii(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toAscii("0123456789"))
            .isEqualTo("0123456789");
        assertThat(toAscii("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toAscii("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");
        assertThat(toAscii("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"))
            .isEqualTo("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

        // 半角カナ。
        assertThat(toAscii("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");

        // 全角英数字。
        assertThat(toAscii("　")).isEqualTo(" ");
        assertThat(toAscii("０１２３４５６７８９"))
            .isEqualTo("0123456789");
        assertThat(toAscii("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"))
            .isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(toAscii("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"))
            .isEqualTo("abcdefghijklmnopqrstuvwxyz");
        assertThat(toAscii("！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝〜￥"))
            .isEqualTo("!\"#$%&'()*+,-./:;<=>?@[＼]^_`{|}〜￥");

        // 全角カタカナ、ひらがな、漢字。
        assertThat(toAscii("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toAscii("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toAscii("漢字")).isEqualTo("漢字");
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
    @DisplayName("全角英数字へ正しく変換されること。")
    void testToFullwidthAlnum() {
        assertThat(toFullwidthAlnum(null)).isNull();
        assertThat(toFullwidthAlnum("")).isEqualTo("");
        assertThat(toFullwidthAlnum(" ")).isEqualTo(" ");

        // 半角英数字。
        assertThat(toFullwidthAlnum("0123456789"))
            .isEqualTo("０１２３４５６７８９");
        assertThat(toFullwidthAlnum("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
            .isEqualTo("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
        assertThat(toFullwidthAlnum("abcdefghijklmnopqrstuvwxyz"))
            .isEqualTo("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
        assertThat(toFullwidthAlnum("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"))
            .isEqualTo("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

        // 半角カナ。
        assertThat(toFullwidthAlnum("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ"))
            .isEqualTo("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ");

        // 全角英数字。
        assertThat(toFullwidthAlnum("　")).isEqualTo("　");
        assertThat(toFullwidthAlnum("０１２３４５６７８９"))
            .isEqualTo("０１２３４５６７８９");
        assertThat(toFullwidthAlnum("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"))
            .isEqualTo("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
        assertThat(toFullwidthAlnum("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"))
            .isEqualTo("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
        assertThat(toFullwidthAlnum("！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝〜￥"))
            .isEqualTo("！＂＃＄％＆＇（）＊＋，－．／：；＜＝＞？＠［＼］＾＿｀｛｜｝〜￥");

        // 全角カタカナ、ひらがな、漢字。
        assertThat(toFullwidthAlnum("アイウエオカキクケコサシスセソタチツテトナニヌネノ"))
            .isEqualTo("アイウエオカキクケコサシスセソタチツテトナニヌネノ");
        assertThat(toFullwidthAlnum("あいうえおかきくけこさしすせそたちつてとなにぬねの"))
            .isEqualTo("あいうえおかきくけこさしすせそたちつてとなにぬねの");
        assertThat(toFullwidthAlnum("漢字")).isEqualTo("漢字");
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

    @Test
    @DisplayName("JIS 1990 へ正しく変換されること。")
    void testToJis1990() {
        String source =
                "\u2014\u2015\u301C\uFF5E\u2016\u2225\u2212\uFF0D"
                + "\u00A2\uFFE0\u00A3\uFFE1\u00AC\uFFE2∑＇＂"
                + "悅晴淸益礼靖精羽逸閒靑飯飼館髙鶴"
                + "纊塚增寬德朗橫瀨猪甁神祥福綠緖薰諸賴郞都鄕隆黑"
                + "俱侮俠倂僧免勉勤卑卽啞喝嘆器嚙囊塡塀墨剝𠮟屢層屮吞噓巢廊徵悔慨憎懲戾揭搔摑擊攢敏"
                + "既晚暑曆梅槪欄步歷殺每海涉淚渚渴溫漢潑瀆焰煮狀琢硏碑社祉祈祐祖祝禍禎禱穀突節簞緣"
                + "練繁繡署者臭萊著蔣虛虜蟬蠟褐視謁謹賓贈軀逸醬醱錄鍊難響頰頻顚類驒鷗鹼麴麵黃姸屛幷瘦繫"
                + "縉";

        assertThat(decode(encode(source, SHIFT_JIS, "〓"), SHIFT_JIS)).isEqualTo(
                "\u2014〓\u301C〓\u2016〓\u2212〓"
                + "\u00A2〓\u00A3〓\u00AC〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓");

        String target =
                "\u2014\u2014\u301C\u301C\u2016\u2016\u2212\u2212"
                + "\u00A2\u00A2\u00A3\u00A3\u00AC\u00ACΣ'\""
                + "悦晴清益礼靖精羽逸間青飯飼館高鶴"
                + "絋塚増寛徳朗横瀬猪瓶神祥福緑緒薫諸頼郎都郷隆黒"
                + "倶侮侠併僧免勉勤卑即唖喝嘆器噛嚢填塀墨剥叱屡層屮呑嘘巣廊徴悔慨憎懲戻掲掻掴撃攅敏"
                + "既晩暑暦梅概欄歩歴殺毎海渉涙渚渇温漢溌涜焔煮状琢研碑社祉祈祐祖祝禍禎祷穀突節箪縁"
                + "練繁繍署者臭莱著蒋虚虜蝉蝋褐視謁謹賓贈躯逸醤醗録錬難響頬頻顛類騨鴎鹸麹麺黄妍屏并痩繋"
                + "縉";

        assertThat(toJis1990(source)).isEqualTo(target);

        byte[] bytes = encode(target, SHIFT_JIS, "〓");
        assertThat(decode(bytes, SHIFT_JIS)).isEqualTo(target);
        assertThat(decode(bytes, SHIFT_JIS_2004)).isEqualTo(target);
        assertThat(toJis1990(decode(bytes, WINDOWS_31J))).isEqualTo(target);
    }

    @Test
    @DisplayName("JIS 2004 へ正しく変換されること。")
    void testToJis2004() {
        String source =
                "\u2014\u2015\u00A2\uFFE0\u00A3\uFFE1\u00AC\uFFE2∑"
                + "悅晴淸益礼靖精羽逸閒靑飯飼館髙鶴";

        String source2 = "￤";

        String source3 =
                "\u301C\uFF5E\u2016\u2225\u2212\uFF0D"
                + "纊塚增寬德朗橫瀨猪甁神祥福綠緖薰諸賴郞都鄕隆黑"
                + "俱侮俠倂僧免勉勤卑卽啞喝嘆器嚙囊塡塀墨剝𠮟屢層屮吞噓巢廊徵悔慨憎懲戾揭搔摑擊攢敏"
                + "既晚暑曆梅槪欄步歷殺每海涉淚渚渴溫漢潑瀆焰煮狀琢硏碑社祉祈祐祖祝禍禎禱穀突節簞緣"
                + "練繁繡署者臭萊著蔣虛虜蟬蠟褐視謁謹賓贈軀逸醬醱錄鍊難響頰頻顚類驒鷗鹼麴麵黃姸屛幷瘦繫"
                + "縉";

        assertThat(decode(encode(source, SHIFT_JIS_2004, "〓"), SHIFT_JIS_2004)).isEqualTo(
                "\u2014〓\u00A2〓\u00A3〓\u00AC〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

        assertThat(decode(encode(source2, SHIFT_JIS_2004, "〓"), SHIFT_JIS_2004))
            .isEqualTo("〓");

        assertThat(decode(encode(source3, SHIFT_JIS_2004, "〓"), SHIFT_JIS_2004))
            .isEqualTo(source3);

        String target =
                "\u2014\u2014\u00A2\u00A2\u00A3\u00A3\u00AC\u00ACΣ"
                + "悦晴清益礼靖精羽逸間青飯飼館高鶴";

        String target2 = "¦";

        assertThat(toJis2004(source)).isEqualTo(target);
        assertThat(toJis2004(source2)).isEqualTo(target2);
        assertThat(toJis2004(source3)).isEqualTo(source3);

        byte[] bytes = encode(target, SHIFT_JIS_2004, "〓");
        assertThat(decode(bytes, SHIFT_JIS_2004)).isEqualTo(target);
        assertThat(decode(bytes, SHIFT_JIS)).isEqualTo(target);
        assertThat(toJis2004(decode(bytes, WINDOWS_31J, "〓"))).isEqualTo(target);

        assertThat(decode(encode(target2, SHIFT_JIS_2004, "〓"), SHIFT_JIS_2004))
            .isEqualTo(target2);
    }

    @Test
    @DisplayName("Windows-31J へ正しく変換されること。")
    void testToWindows31j() {
        String source =
                "\u2014\u2015\u301C\uFF5E\u2016\u2225\u2212\uFF0D"
                + "\u00A2\uFFE0\u00A3\uFFE1\u00AC\uFFE2"
                + "俱侮俠倂僧免勉勤卑卽啞喝嘆器嚙囊塡塀墨剝𠮟屢層屮吞噓巢廊徵悔慨憎懲戾揭搔摑擊攢敏"
                + "既晚暑曆梅槪欄步歷殺每海涉淚渚渴溫漢潑瀆焰煮狀琢硏碑社祉祈祐祖祝禍禎禱穀突節簞緣"
                + "練繁繡署者臭萊著蔣虛虜蟬蠟褐視謁謹賓贈軀逸醬醱錄鍊難響頰頻顚類驒鷗鹼麴麵黃姸屛幷瘦繫"
                + "縉";

        String source2 = "¦";

        String source3 =
                "∑"
                + "悅晴淸益礼靖精羽逸閒靑飯飼館髙鶴"
                + "纊塚增寬德朗橫瀨猪甁神祥福綠緖薰諸賴郞都鄕隆黑";

        assertThat(decode(encode(source, WINDOWS_31J, "〓"), WINDOWS_31J)).isEqualTo(
                "〓\u2015〓\uFF5E〓\u2225〓\uFF0D"
                + "\uFFE0\uFFE0\uFFE1\uFFE1\uFFE2\uFFE2"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓"
                + "〓");

        assertThat(decode(encode(source2, WINDOWS_31J, "〓"), WINDOWS_31J))
            .isEqualTo("〓");

        assertThat(decode(encode(source3, WINDOWS_31J, "〓"), WINDOWS_31J))
            .isEqualTo(source3);

        String target =
                "\u2015\u2015\uFF5E\uFF5E\u2225\u2225\uFF0D\uFF0D"
                + "\uFFE0\uFFE0\uFFE1\uFFE1\uFFE2\uFFE2"
                + "倶侮侠併僧免勉勤卑即唖喝嘆器噛嚢填塀墨剥叱屡層屮呑嘘巣廊徴悔慨憎懲戻掲掻掴撃攅敏"
                + "既晩暑暦梅概欄歩歴殺毎海渉涙渚渇温漢溌涜焔煮状琢研碑社祉祈祐祖祝禍禎祷穀突節箪縁"
                + "練繁繍署者臭莱著蒋虚虜蝉蝋褐視謁謹賓贈躯逸醤醗録錬難響頬頻顛類騨鴎鹸麹麺黄妍屏并痩繋"
                + "縉";

        String target2 = "￤";

        assertThat(toWindows31j(source)).isEqualTo(target);
        assertThat(toWindows31j(source2)).isEqualTo(target2);
        assertThat(toWindows31j(source3)).isEqualTo(source3);

        byte[] bytes = encode(target, WINDOWS_31J, "〓");
        assertThat(decode(bytes, WINDOWS_31J)).isEqualTo(target);
        assertThat(toWindows31j(decode(bytes, SHIFT_JIS, "〓"))).isEqualTo(target);
        assertThat(toWindows31j(decode(bytes, SHIFT_JIS_2004, "〓"))).isEqualTo(target);

        assertThat(decode(encode(target2, WINDOWS_31J, "〓"), WINDOWS_31J))
        .isEqualTo(target2);
    }

    @Test
    @DisplayName("Shift_JIS のエンコード・デコードのテスト。")
    void testShiftJis() {
        final byte[] bytes = ArrayUtils.bytes(
            0x81, 0x5C,
            0x81, 0x60,
            0x81, 0x61,
            0x81, 0x7C,
            0x81, 0x91,
            0x81, 0x92,
            0x81, 0xCA
        );

        assertThat(encode(null, SHIFT_JIS)).isNull();
        assertThat(encode("", SHIFT_JIS)).isEmpty();
        assertThat(encode("\u2014〜‖\u2212¢£¬", SHIFT_JIS)).isEqualTo(bytes);
        assertThatThrownBy(() -> encode("\u2015～∥\uFF0D￠￡￢", SHIFT_JIS))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(encode("\u2015～∥\uFF0D￠￡￢", SHIFT_JIS, "〓")).containsExactly(
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC
        );
        assertThat(encode("①髙", SHIFT_JIS, null)).containsExactly('?', '?');

        assertThat(decode(null, SHIFT_JIS)).isNull();
        assertThat(decode(new byte[0], SHIFT_JIS)).isEqualTo("");
        assertThat(decode(ArrayUtils.bytes(0x5C, 0x7E), SHIFT_JIS)).isEqualTo("\\~");
        assertThatThrownBy(() -> decode(ArrayUtils.bytes(0x80), SHIFT_JIS))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(decode(ArrayUtils.bytes(0x80), SHIFT_JIS, null)).isEqualTo("\uFFFD");
        assertThat(decode(bytes, SHIFT_JIS)).isEqualTo("\u2014〜‖\u2212¢£¬");
    }

    @Test
    @DisplayName("Shift_JIS-2004 のエンコード・デコードのテスト。")
    void testShiftJis2004() {
        final byte[] bytes = ArrayUtils.bytes(
            0x81, 0x5C,
            0x81, 0x60,
            0x81, 0x61,
            0x81, 0x7C,
            0x81, 0x91,
            0x81, 0x92,
            0x81, 0xCA
        );

        assertThat(encode(null, SHIFT_JIS_2004)).isNull();
        assertThat(encode("", SHIFT_JIS_2004)).isEmpty();
        assertThat(encode("\u2014〜‖\u2212¢£¬", SHIFT_JIS_2004)).isEqualTo(bytes);
        assertThatThrownBy(() -> encode("\u2015～∥\uFF0D￠￡￢", SHIFT_JIS_2004))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(encode("\u2015～∥\uFF0D￠￡￢", SHIFT_JIS_2004, "〓")).containsExactly(
            0x81, 0xAC,
            0x81, 0xB0,
            0x81, 0xD2,
            0x81, 0xAF,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC
        );
        assertThat(encode("①髙", SHIFT_JIS_2004, null)).containsExactly(
            0x87, 0x40,
            '?'
        );

        assertThat(decode(null, SHIFT_JIS_2004)).isNull();
        assertThat(decode(new byte[0], SHIFT_JIS_2004)).isEqualTo("");
        assertThat(decode(ArrayUtils.bytes(0x5C, 0x7E), SHIFT_JIS_2004)).isEqualTo("\\~");
        assertThatThrownBy(() -> decode(ArrayUtils.bytes(0x80), SHIFT_JIS_2004))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(decode(ArrayUtils.bytes(0x80), SHIFT_JIS_2004, null)).isEqualTo("\uFFFD");
        assertThat(decode(bytes, SHIFT_JIS_2004)).isEqualTo("\u2014〜‖\u2212¢£¬");
    }

    @Test
    @DisplayName("Windows-31J のエンコード・デコードのテスト。")
    void testWindows31j() {
        final byte[] bytes = ArrayUtils.bytes(
            0x81, 0x5C,
            0x81, 0x60,
            0x81, 0x61,
            0x81, 0x7C,
            0x81, 0x91,
            0x81, 0x92,
            0x81, 0xCA
        );

        assertThat(encode(null, WINDOWS_31J)).isNull();
        assertThat(encode("", WINDOWS_31J)).isEmpty();
        assertThat(encode("\u2015～∥\uFF0D￠￡￢", WINDOWS_31J)).isEqualTo(bytes);
        assertThatThrownBy(() -> encode("\u2014〜‖\u2212¢£¬", WINDOWS_31J))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(encode("\u2014〜‖\u2212¢£¬", WINDOWS_31J, "〓")).containsExactly(
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0xAC,
            0x81, 0x91,
            0x81, 0x92,
            0x81, 0xCA
        );
        assertThat(encode("①髙", WINDOWS_31J, null)).containsExactly(
            0x87, 0x40,
            0xFB, 0xFC
        );

        assertThat(decode(null, WINDOWS_31J)).isNull();
        assertThat(decode(new byte[0], WINDOWS_31J)).isEqualTo("");
        assertThat(decode(ArrayUtils.bytes(0x5C, 0x7E), WINDOWS_31J)).isEqualTo("\\~");
        assertThatThrownBy(() -> decode(ArrayUtils.bytes(0x80), WINDOWS_31J))
            .isInstanceOf(CharacterCodingRuntimeException.class);
        assertThat(decode(ArrayUtils.bytes(0x80), WINDOWS_31J, null)).isEqualTo("\uFFFD");
        assertThat(decode(bytes, WINDOWS_31J)).isEqualTo("\u2015～∥\uFF0D￠￡￢");
    }

}
