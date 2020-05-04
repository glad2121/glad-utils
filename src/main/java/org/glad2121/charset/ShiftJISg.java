package org.glad2121.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 拡張版 Shift_JIS 文字集合。
 *
 * @author glad2121
 */
class ShiftJISg extends Charset {

    /**
     * 移譲先の文字集合。
     */
    final Charset delegate = Charset.forName("Shift_JIS");

    /**
     * コンストラクタ。
     */
    ShiftJISg() {
        super("x-Shift_JIS-g", new String[] {"x-SJIS-g"});
    }

    /**
     * 指定された文字集合がこの文字集合に含まれるか判定します。
     */
    @Override
    public boolean contains(Charset cs) {
        return (cs instanceof ShiftJISg) || delegate.contains(cs);
    }

    /**
     * エンコーダを生成して返します。
     */
    @Override
    public CharsetEncoder newEncoder() {
        return new ConverterEncoder(delegate.newEncoder(),
                CodePointConverters.TO_JIS_1990);
    }

    /**
     * デコーダを生成して返します。
     */
    @Override
    public CharsetDecoder newDecoder() {
        return new ShiftJISDecoder(delegate.newDecoder());
    }

}
