package org.glad2121.charset;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link CodePointSet} の単体テスト。
 */
class CodePointSetTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    @DisplayName("コードポイントの個数のチェック。")
    void testCount() {
        // US-ASCII 128字から、制御文字33字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CharType::isAscii)).isEqualTo(95);
        // 半角カナ63字と、円記号、上線。
        assertThat(CodePointSet.INSTANCE.count(CharType::isJisX0201Ext)).isEqualTo(65);
        // JIS X 0208-1990 6879字と、Windows-31J とマッピングが異なる7字。
        assertThat(CodePointSet.INSTANCE.count(CharType::isJisX0208)).isEqualTo(6879);
        // NEC特殊文字83字から、JIS X 0208 との重複9字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CharType::isNecSpecialChar)).isEqualTo(74);
        // IBM拡張漢字388字から、JIS X 0208、NEC特殊文字との重複15字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CharType::isIbmExt)).isEqualTo(373);
        // JIS X 0213:2004 第3水準漢字1259字、追加非漢字659字から、結合文字列25字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CharType::isJisX0213P1Ext)).isEqualTo(1893);
        // JIS X 0213:2004 第4水準漢字2436字。
        assertThat(CodePointSet.INSTANCE.count(CharType::isJisX0213P2)).isEqualTo(2436);
    }

    @Test
    @DisplayName("charset.txt から読み込んだ結果と同じになることを確認。")
    void testEqualToOriginal() {
        CodePointSet source = new CharsetFileConverter()
                .loadFromCharsetText(CharsetFileConverter.SOURCE_NAME);
        CodePointSet target = CodePointSet.INSTANCE;
        Map<Integer, CharType> diff = source.map.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(target.map.get(entry.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (!diff.isEmpty()) {
            int count = 0;
            for (var entry : new TreeMap<>(diff).entrySet()) {
                System.err.printf("%04X: %s%n", entry.getKey(), entry.getValue());
                if (++count > 100) break;
            }
            fail("diff is not empty: " + diff.size());
        }
    }

}
