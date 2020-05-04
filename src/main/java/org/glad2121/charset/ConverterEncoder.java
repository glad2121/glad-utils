package org.glad2121.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.function.IntUnaryOperator;

/**
 * コンバータを適用した文字集合エンコーダ。
 *
 * @author glad2121
 */
class ConverterEncoder extends CharsetEncoder {

    /**
     * 移譲先のエンコーダ。
     */
    final CharsetEncoder delegate;

    /**
     * 適用するコンバータ。
     */
    final IntUnaryOperator converter;

    /**
     * コンストラクタ。
     *
     * @param delegate 移譲先のエンコーダ
     * @param converter 適用するコンバータ
     */
    ConverterEncoder(CharsetEncoder delegate, IntUnaryOperator converter) {
        super(delegate.charset(),
                delegate.averageBytesPerChar(),
                delegate.maxBytesPerChar(),
                delegate.replacement());
        this.delegate = delegate;
        this.converter = converter;
    }

    /**
     * 1個以上の文字を1個以上のバイトにエンコードします。
     */
    @Override
    protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
        int mark = in.position();
        try {
            CharBuffer buf = CharBuffer.allocate(2);
            while (in.hasRemaining()) {
                char c = in.get();
                if (Character.isSurrogate(c)) {
                    // サロゲートペアの場合。
                    if (!in.hasRemaining()) {
                        return CoderResult.unmappableForLength(1);
                    }
                    char c2 = in.get();
                    if (!Character.isSurrogate(c2)) {
                        return CoderResult.unmappableForLength(1);
                    }
                    int cp = Character.toCodePoint(c, c2);
                    // コンバータを適用。
                    cp = convert(cp);
                    append(buf.clear(), cp);
                } else {
                    // 基本多言語面の場合。
                    // コンバータを適用。
                    int cp = convert(c);
                    append(buf.clear(), cp);
                }
                CoderResult result = doEncode(buf.flip(), out);
                if (result.isError()) {
                    return result;
                }
                // エンコードが成功した場合は位置を更新する。
                mark = in.position();
            }
            return CoderResult.UNDERFLOW;
        } finally {
            in.position(mark);
        }
    }

    /**
     * 指定されたコードポイントを変換します。
     *
     * @param codePoint 変換前のコードポイント
     * @return 変換後のコードポイント
     */
    int convert(int codePoint) {
        return converter.applyAsInt(codePoint);
    }

    /**
     * バッファにコードポイントを追加します。
     *
     * @param buf バッファ
     * @param codePoint コードポイント
     */
    void append(CharBuffer buf, int codePoint) {
        if (Character.isBmpCodePoint(codePoint)) {
            buf.append((char) codePoint);
        } else {
            buf.append(Character.highSurrogate(codePoint));
            buf.append(Character.lowSurrogate(codePoint));
        }
    }

    /**
     * 実際にエンコードを行います。
     *
     * @param in  入力文字バッファ
     * @param out 出力バイトバッファ
     * @return 実行結果
     */
    CoderResult doEncode(CharBuffer in, ByteBuffer out) {
        return delegate.encode(in, out, false);
    }

}
