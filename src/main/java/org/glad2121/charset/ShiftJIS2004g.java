package org.glad2121.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 拡張版 Shift_JIS-2004 文字集合。
 *
 * @author glad2121
 */
class ShiftJIS2004g extends Charset {

    /**
     * 移譲先の文字集合。
     */
    final Charset delegate = Charset.forName("x-SJIS_0213");

    /**
     * コンストラクタ。
     */
    ShiftJIS2004g() {
        super("x-Shift_JIS-2004-g", new String[] {"x-SJIS-2004-g"});
    }

    /**
     * 指定された文字集合がこの文字集合に含まれるか判定します。
     */
    @Override
    public boolean contains(Charset cs) {
        return (cs instanceof ShiftJIS2004g) || delegate.contains(cs);
    }

    /**
     * エンコーダを生成して返します。
     */
    @Override
    public CharsetEncoder newEncoder() {
        return new ConverterEncoder(delegate.newEncoder(),
                CodePointConverters.TO_JIS_2004);
    }

    /**
     * デコーダを生成して返します。
     */
    @Override
    public CharsetDecoder newDecoder() {
        return new ShiftJISDecoder(delegate.newDecoder());
    }

}
