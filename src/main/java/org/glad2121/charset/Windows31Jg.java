package org.glad2121.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 拡張版 Windows-31J 文字集合。
 *
 * @author glad2121
 */
class Windows31Jg extends Charset {

    /**
     * 移譲先の文字集合。
     */
    final Charset delegate = Charset.forName("Windows-31J");

    /**
     * コンストラクタ。
     */
    Windows31Jg() {
        super("x-Windows-31J-g", new String[] {"x-MS932-g"});
    }

    /**
     * 指定された文字集合がこの文字集合に含まれるか判定します。
     */
    @Override
    public boolean contains(Charset cs) {
        return (cs instanceof Windows31Jg) || delegate.contains(cs);
    }

    /**
     * エンコーダを生成して返します。
     */
    @Override
    public CharsetEncoder newEncoder() {
        return new ConverterEncoder(delegate.newEncoder(),
                CodePointConverters.TO_WINDOWS_31J);
    }

    /**
     * デコーダを生成して返します。
     */
    @Override
    public CharsetDecoder newDecoder() {
        return new ShiftJISDecoder(delegate.newDecoder());
    }

}
