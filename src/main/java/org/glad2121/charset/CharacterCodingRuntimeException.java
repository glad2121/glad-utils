package org.glad2121.charset;

import java.nio.charset.CharacterCodingException;

/**
 * 文字のエンコード、デコードでエラーが発生した時にスローされる実行時例外。
 *
 * @author glad2121
 * @see CharacterCodingException
 */
public class CharacterCodingRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ。
     *
     * @param cause 原因となった例外
     */
    public CharacterCodingRuntimeException(CharacterCodingException cause) {
        super(cause);
    }

    /**
     * 原因となった例外を返します。
     */
    @Override
    public CharacterCodingException getCause() {
        return (CharacterCodingException) super.getCause();
    }

}
