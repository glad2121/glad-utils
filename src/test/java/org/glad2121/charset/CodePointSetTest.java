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
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isAscii)).isEqualTo(95);
        // 半角カナ63字と、円記号、上線。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isJisX0201Ext)).isEqualTo(65);
        // JIS X 0208-1990 6879字と、Windows-31J とマッピングが異なる7字。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isJisX0208)).isEqualTo(6886);
        // NEC特殊文字83字から、JIS X 0208 との重複9字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isNecSpecialChar)).isEqualTo(74);
        // IBM拡張漢字388字から、JIS X 0208、NEC特殊文字との重複15字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isIbmExt)).isEqualTo(373);
        // JIS X 0213:2004 第3水準漢字1259字、追加非漢字659字から、
        // Windows-31J との重複275字、結合文字列25字を除き、結合文字2字を追加したもの。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isJisX0213P1Ext)).isEqualTo(1620);
        // JIS X 0213:2004 第4水準漢字2436字から、Windows-31J との重複89字を除いたもの。
        assertThat(CodePointSet.INSTANCE.count(CodePointSet::isJisX0213P2)).isEqualTo(2347);
    }

    @Test
    @DisplayName("charset.txt から読み込んだ結果と同じになることを確認。")
    void testEqualToOriginal() {
        CodePointSet source = new CharsetFileConverter()
                .loadFromCharsetText(CharsetFileConverter.SOURCE_NAME);
        CodePointSet target = CodePointSet.INSTANCE;
        Map<Integer, Integer> diff = source.map.entrySet().stream()
                .filter(entry -> target.map.get(entry.getKey()) != entry.getValue())
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
