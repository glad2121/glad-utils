package org.glad2121.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.function.IntUnaryOperator;

/**
 * 拡張版 Windows-31J と JIS 第3水準漢字を採用した文字集合。
 *
 * @author glad2121
 */
class Windows31J2004g extends Charset {

    /**
     * 移譲先の Windows-31J 文字集合。
     */
    final Charset delegate = Charset.forName("Windows-31J");

    /**
     * 移譲先の Shift_JIS-2004 文字集合。
     */
    final Charset sjis2004 = Charset.forName("x-SJIS_0213");

    /**
     * コンストラクタ。
     */
    Windows31J2004g() {
        super("x-Windows-31J-2004-g", new String[] {"x-MS932-2004-g"});
    }

    /**
     * 指定された文字集合がこの文字集合に含まれるか判定します。
     */
    @Override
    public boolean contains(Charset cs) {
        return (cs instanceof Windows31J2004g) || delegate.contains(cs);
    }

    /**
     * エンコーダを生成して返します。
     */
    @Override
    public CharsetEncoder newEncoder() {
        return new Encoder(delegate.newEncoder(), sjis2004.newEncoder(),
                CodePointConverters.TO_WINDOWS_31J_2004);
    }

    /**
     * デコーダを生成して返します。
     */
    @Override
    public CharsetDecoder newDecoder() {
        return new Decoder(delegate.newDecoder(), sjis2004.newDecoder());
    }

    /**
     * 文字集合エンコーダ。
     */
    static class Encoder extends ConverterEncoder {

        /**
         * 移譲先の Shift_JIS-2004 エンコーダ。
         */
        final CharsetEncoder sjis2004;

        /**
         * コンストラクタ。
         *
         * @param delegate 移譲先の Windows-31J エンコーダ
         * @param sjis2004 移譲先の Shift_JIS-2004 エンコーダ
         * @param converter 適用するコンバータ
         */
        Encoder(CharsetEncoder delegate, CharsetEncoder sjis2004,
                IntUnaryOperator converter) {
            super(delegate, converter);
            this.sjis2004 = sjis2004;
        }

        /**
         * 実際にエンコードを行います。
         */
        @Override
        CoderResult doEncode(CharBuffer in, ByteBuffer out) {
            // まずは、Windows-31J でエンコード。
            CoderResult result = super.doEncode(in, out);
            if (!result.isUnmappable()) {
                return result;
            }

            // Windows-31J でエンコードできない場合、Shift_JIS-2004 で再試行。
            int ss = in.position();
            int ds = out.position();
            CoderResult result2 = sjis2004.encode(in, out, false);
            if (result2.isError()) {
                return result;
            }
            int de = out.position();
            int length = de - ds;
            if (length == 2) {
                byte[] bytes = new byte[length];
                out.position(ds);
                out.get(bytes);
                if ((bytes[0] & 0xFF) >= 0xF0) {
                    // 第4水準漢字は不採用。
                    in.position(ss);
                    out.position(ds);
                    return result;
                }
            }

            return CoderResult.UNDERFLOW;
        }

    }

    /**
     * 文字集合デコーダ。
     */
    static class Decoder extends ShiftJISDecoder {

        /**
         * 移譲先の Shift_JIS-2004 デコーダ。
         */
        final CharsetDecoder sjis2004;

        /**
         * コンストラクタ。
         *
         * @param delegate 移譲先の Windows-31J デコーダ
         * @param sjis2004 移譲先の Shift_JIS-2004 デコーダ
         */
        Decoder(CharsetDecoder delegate, CharsetDecoder sjis2004) {
            super(delegate);
            this.sjis2004 = sjis2004;
        }

        /**
         * 実際にデコードを行います。
         */
        @Override
        CoderResult doDecode(ByteBuffer in, CharBuffer out) {
            byte[] bytes = in.array();
            int n = bytes[0] & 0xFF;

            // まずは、Windows-31J でデコード。
            CoderResult result = null;
            if (!(0xED <= n && n <= 0xEE)) {
                // NEC 選定 IBM 拡張文字以外を対象とする。
                result = super.doDecode(in, out);
                if (!result.isError()) {
                    return result;
                }
            }

            // Windows-31J でデコードできない場合、Shift_JIS-2004 で再試行。
            if (0x81 <= n && n <= 0xFE) {
                // 非漢字・第3水準漢字のみ対象とする。
                CoderResult result2 = sjis2004.decode(in, out, false);
                if (!result2.isError()) {
                    return CoderResult.UNDERFLOW;
                }
                if (result == null) {
                    return result2;
                }
            }

            return result;
        }

    }

}
