package org.glad2121.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link CodePointConverters} の単体テスト。
 */
class CodePointConvertersTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("定数定義のチェック。")
    void testConstants() {
        // 半角カナ・全角カタカナ・全角ひらがなで互換性のある文字。
        assertThat(CodePointConverters.HALFWIDTH_PUNCTATIONS).hasSize(6);
        assertThat(CodePointConverters.HALFWIDTH_KATAKANA).hasSize(55);
        assertThat(CodePointConverters.FULLWIDTH_PUNCTATIONS).hasSize(6);
        assertThat(CodePointConverters.FULLWIDTH_KATAKANA).hasSize(55);
        assertThat(CodePointConverters.FULLWIDTH_HIRAGANA).hasSize(55);
    }

}
