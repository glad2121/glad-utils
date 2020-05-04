package org.glad2121.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * Shift_JIS および派生文字集合のデコーダ。
 *
 * @author glad2121
 */
class ShiftJISDecoder extends CharsetDecoder {

    /**
     * 移譲先のデコーダ。
     */
    final CharsetDecoder delegate;

    /**
     * コンストラクタ。
     *
     * @param delegate 移譲先のデコーダ
     */
    ShiftJISDecoder(CharsetDecoder delegate) {
        super(delegate.charset(),
                delegate.maxCharsPerByte(),
                delegate.maxCharsPerByte());
        this.delegate = delegate;
    }

    /**
     * 1個以上のバイトを1個以上の文字にデコードします。
     */
    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
        int mark = in.position();
        try {
            ByteBuffer buf = ByteBuffer.allocate(2);
            while (in.hasRemaining()) {
                byte n = in.get();
                if (isSjisHi(n & 0xFF)) {
                    // 2バイト文字の場合。
                    if (!in.hasRemaining()) {
                        return CoderResult.malformedForLength(1);
                    }
                    byte n2 = in.get();
                    if (!isSjisLo(n2 & 0xFF)) {
                        return CoderResult.malformedForLength(1);
                    }
                    buf.clear().put(n).put(n2);
                    CoderResult result = doDecode(buf.flip(), out);
                    if (result.isError()) {
                        // 2バイトまとめてエラーにする。
                        return CoderResult.unmappableForLength(2);
                    }
                    if (!result.isUnderflow()) {
                        return result;
                    }
                } else {
                    // 1バイト文字の場合。
                    buf.clear().put(n);
                    CoderResult result = doDecode(buf.flip(), out);
                    if (!result.isUnderflow()) {
                        return result;
                    }
                }
                // デコードに成功した場合は位置を更新する。
                mark = in.position();
            }
            return CoderResult.UNDERFLOW;
        } finally {
            in.position(mark);
        }
    }

    /**
     * 指定された値が Shift_JIS の上位バイトか判定します。
     *
     * @param n テストする値
     * @return 上位バイトならば {@code true}
     */
    boolean isSjisHi(int n) {
        return (0x81 <= n && n <= 0x9F) || (0xE0 <= n && n <= 0xFC);
    }

    /**
     * 指定された値が Shift_JIS の下位バイトか判定します。
     *
     * @param n テストする値
     * @return 下位バイトならば {@code true}
     */
    boolean isSjisLo(int n) {
        return 0x40 <= n && n <= 0xFC && n != 0x7F;
    }

    /**
     * 実際にデコードを行います。
     *
     * @param in  入力バイトバッファ
     * @param out 出力文字バッファ
     * @return 実行結果
     */
    CoderResult doDecode(ByteBuffer in, CharBuffer out) {
        delegate.reset();
        CoderResult result = delegate.decode(in, out, true);
        if (result.isUnderflow()) {
            result = delegate.flush(out);
        }
        return result;
    }

}
