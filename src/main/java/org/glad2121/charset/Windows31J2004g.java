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
            int pos = in.position();
            char c = in.get();
            in.position(pos);

            // まずは、Windows-31J でエンコード。
            CoderResult result = null;
            if (c == '\u2015' || c == '\u2211'
                    || (c >= '\u3400' && c != '\uFF0D' && c != '\uFF5E')) {
                // 水平線、総和、漢字を対象とする。
                // 全角記号を対象とするが、全角ハイフンマイナスと全角チルダを除く。
                result = super.doEncode(in, out);
                if (!result.isError()) {
                    return result;
                }
            }

            // Windows-31J でエンコードできない場合、Shift_JIS-2004 で再試行。
            int ss = in.position();
            int ds = out.position();
            sjis2004.reset();
            CoderResult result2 = sjis2004.encode(in, out, true);
            if (!result2.isUnderflow()) {
                return result2;
            }
            sjis2004.flush(out);

            int length = out.position() - ds;
            if (length == 2) {
                byte[] bytes = new byte[length];
                out.position(ds);
                out.get(bytes);
                if ((bytes[0] & 0xFF) <= 0xEF) {
                    // 第4水準漢字以外を採用。
                    return result2;
                }
            }
            in.position(ss);
            out.position(ds);
            return CoderResult.unmappableForLength(1);
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
            int pos = in.position();
            int n1 = in.get() & 0xFF;
            int n2 = (in.hasRemaining()) ? (in.get() & 0xFF) : -1;
            in.position(pos);

            // まずは、Windows-31J でデコード。
            CoderResult result = null;
            if (n1 == 0x81 && (n2 == 0x91 || n2 == 0x92 || n2 == 0xCA)
                    || n1 == 0x87 || n1 >= 0xF0) {
                // 全角記号、NEC 特殊文字、IBM 拡張文字を対象とする。
                result = super.doDecode(in, out);
                if (!result.isError()) {
                    return result;
                }
            }

            // Windows-31J でデコードできない場合、Shift_JIS-2004 で再試行。
            if (n1 <= 0xEF) {
                // 第4水準漢字以外を対象とする。
                sjis2004.reset();
                result = sjis2004.decode(in, out, true);
                if (result.isUnderflow()) {
                    sjis2004.flush(out);
                }
            }

            return result;
        }

    }

}
