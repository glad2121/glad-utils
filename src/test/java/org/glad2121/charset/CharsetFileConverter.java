package org.glad2121.charset;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glad2121.charset.CodePointSet.CodeType;

/**
 * 文字集合の設定ファイルをコンパクト化するコンバータ。
 *
 * @author glad2121
 */
class CharsetFileConverter {

    /**
     * リソースのパス。
     */
    static final String RESOURCE_PATH = "src/main/resources";

    /**
     * 入力ファイル名。
     */
    static final String SOURCE_NAME = "charset.txt";

    /**
     * 出力ファイル名。
     */
    static final String TARGET_NAME = CodePointSet.SOURCE_NAME;

    /**
     * メインルーチン。
     */
    public static void main(String[] args) {
        new CharsetFileConverter().convert();
    }

    /**
     * ファイルを変換します。
     */
    void convert() {
        CodePointSet set = loadFromCharsetText(SOURCE_NAME);

        assertThat(set.count(CodeType.UNKNOWN)).isEqualTo(0);
        assertThat(set.count(CodeType.US_ASCII)).isEqualTo(95);
        assertThat(set.count(CodeType.JIS_X_0201)).isEqualTo(65);
        assertThat(set.count(CodeType.JIS_X_0208)).isEqualTo(6886);
        assertThat(set.count(CodeType.NEC_SPECIAL_CHAR)).isEqualTo(74);
        assertThat(set.count(CodeType.IBM_EXT)).isEqualTo(373);
        assertThat(set.count(CodeType.JIS_X_0213_3)).isEqualTo(1620);
        assertThat(set.count(CodeType.JIS_X_0213_4)).isEqualTo(2347);

        Path path = Paths.get(RESOURCE_PATH,
                getClass().getPackageName().replace('.', '/'), TARGET_NAME);
        saveToCharsetCompactText(path.toFile(), set);
    }

    /**
     * 入力ファイルを読み込んで、コードポイントの集合を生成します。
     *
     * @param name 入力ファイル名
     * @return コードポイントの集合
     */
    CodePointSet loadFromCharsetText(String name) {
        try (InputStream in = getClass().getResourceAsStream(name)) {
            if (in == null) {
                throw new RuntimeException("Resource not found: " + name);
            }
            Map<String, Integer> count = new TreeMap<>();
            Map<Integer, CodeType> map = new TreeMap<>();
            Pattern p = Pattern.compile("(?:U\\+([0-9A-F]+)|-)\\s*,(\\d),(\\d{6}),.*");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    String code = m.group(1);
                    String detail = m.group(3).replaceFirst("[1-7]$", "x");
                    String prefix = (code == null) ? "-" :
                        detail.endsWith("x") ? "x" : code.substring(0, 1);
                    String key = m.group(2)
                            + ',' + detail.substring(4) + detail.substring(2, 4)
                            + ',' + detail + ',' + prefix;
                    count.put(key, count.getOrDefault(key, 0) + 1);
                    if (m.group(1) != null) {
                        int codePoint = Integer.parseInt(m.group(1), 16);
                        int type = Integer.parseInt(m.group(2));
                        if (0 < type && type < 9) {
                            map.put(codePoint, CodeType.values()[type]);
                        }
                    }
                }
            }
            count.forEach((k, v) -> System.out.println(k + ": " + v));
            return new CodePointSet(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 出力ファイルにコードポイントの集合をコンパクト化して書き込みます。
     *
     * @param file 出力ファイル
     * @param set コードポイントの集合
     */
    void saveToCharsetCompactText(File file, CodePointSet set) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (OutputStream out = new FileOutputStream(file)) {
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(out, StandardCharsets.UTF_8)));
            writer.print("#\n");
            writer.print("# コードポイントとコード区分の対応。\n");
            writer.print("#\n");
            CodeType type = null;
            int prev = -1;
            int first = -1;
            int last = -1;
            for (var entry : set.map.entrySet()) {
                int codePoint = entry.getKey();
                if (entry.getValue() != type
                        || codePoint >= 0x10000
                        || codePoint != last + 1) {
                    printLine(writer, type, prev, first, last);
                    type = entry.getValue();
                    prev = last;
                    first = codePoint;
                }
                last = codePoint;
            }
            printLine(writer, type, prev, first, last);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1行分のデータを書き込みます。
     *
     * @param writer {@code PrintWriter}
     * @param type  コード区分
     * @param prev  前回書き込んだコードポイント
     * @param first 最初のコードポイント
     * @param last  最後のコードポイント
     */
    void printLine(PrintWriter writer, CodeType type, int prev, int first, int last) {
        if (type == null) {
            return;
        }
        if (first < 0x10000) {
            if (prev < 0xFF00 && 0xFF00 <= first) {
                writer.print("\n");
                writer.print("# 半角・全角形\n");
                writer.print("\n");
            } else if (prev < 0x3400 && 0x3400 <= first) {
                writer.print("\n");
                writer.print("# 漢字\n");
                writer.print("\n");
            } else if (prev < 0 && 0 <= first) {
                writer.print("\n");
                writer.print("# 非漢字\n");
                writer.print("\n");
            }
            if (first == last) {
                // 単独のコードポイント。
                writer.printf("%d,\\u%04X\n", type.ordinal(), first);
            } else {
                // コードポイントの範囲。
                writer.printf("%d,\\u%04X-\\u%04X\n", type.ordinal(), first, last);
            }
        } else {
            if (prev < 0x10000) {
                writer.print("\n");
                writer.print("# サロゲートペア\n");
                writer.print("\n");
            }
            // サロゲートペア。
            int hi = Character.highSurrogate(first);
            int lo = Character.lowSurrogate(first);
            writer.printf("%d,\\u%04X\\u%04X\n", type.ordinal(), hi, lo);
        }
    }

}
