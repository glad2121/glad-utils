package org.glad2121.charset;

import java.nio.charset.CharacterCodingException;

import org.glad2121.util.ArrayUtils;
import org.glad2121.util.StringUtils;

/**
 * 文字のエンコード、デコードでエラーが発生した時にスローされる実行時例外。
 *
 * @author glad2121
 * @see CharacterCodingException
 */
public class CharacterCodingRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * エラーの発生位置。
     */
    private final int position;

    /**
     * エラーの発生した文字
     */
    private final Object rejected;

    /**
     * コンストラクタ。
     *
     * @param cause 原因となった例外
     */
    public CharacterCodingRuntimeException(CharacterCodingException cause) {
        super(cause);
        this.position = 0;
        this.rejected = 0;
    }

    /**
     * コンストラクタ。
     *
     * @param position エラーの発生位置
     * @param rejected エラーの発生した文字列
     * @param cause 原因となった例外
     */
    public CharacterCodingRuntimeException(
            int position, CharSequence rejected, CharacterCodingException cause) {
        super(message(position, rejected), cause);
        this.position = position;
        this.rejected = rejected;
    }

    /**
     * コンストラクタ。
     *
     * @param position エラーの発生位置
     * @param rejected エラーの発生したバイト列
     * @param cause 原因となった例外
     */
    public CharacterCodingRuntimeException(
            int position, byte[] rejected, CharacterCodingException cause) {
        super(message(position, rejected), cause);
        this.position = position;
        this.rejected = rejected;
    }

    /**
     * メッセージを生成します。
     *
     * @param position エラーの発生位置
     * @param rejected エラーの発生した文字列
     * @return メッセージ
     */
    static String message(int position, CharSequence rejected) {
        if (StringUtils.isEmpty(rejected)) {
            return "position: " + position;
        } else if (rejected.chars().anyMatch(c ->
                Character.isISOControl(c) || !Character.isDefined(c))) {
            return String.format("position: %d, rejected: %s",
                    position, StringUtils.toUnicodeEscape(rejected));
        } else {
            return String.format("position: %d, rejected: %s (%s)",
                    position, StringUtils.toUnicodeEscape(rejected), rejected);
        }
    }

    /**
     * メッセージを生成します。
     *
     * @param position エラーの発生位置
     * @param rejected エラーの発生したバイト列
     * @return メッセージ
     */
    static String message(int position, byte[] rejected) {
        if (rejected == null || rejected.length == 0) {
            return "position: " + position;
        } else {
            return String.format("position: %d, rejected: 0x%s",
                    position, ArrayUtils.toHexString(rejected));
        }
    }

    /**
     * 原因となった例外を返します。
     */
    @Override
    public CharacterCodingException getCause() {
        return (CharacterCodingException) super.getCause();
    }

    /**
     * エラーの発生位置を返します。
     *
     * @return エラーの発生位置
     */
    public int getPosition() {
        return position;
    }

    /**
     * エラーの発生した文字を返します。
     *
     * @return エラーの発生した文字
     */
    public Object getRejected() {
        return rejected;
    }

}
