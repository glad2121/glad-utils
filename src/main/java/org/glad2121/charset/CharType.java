package org.glad2121.charset;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 文字区分。
 * <pre>
 * No 規格等         補足情報
 * -- -------------- -----------
 * 0 制御文字       制御文字
 * 1 ASCII          BasicJ
 * 2 JIS X 0201     互換文字
 * 3 JIS X 0208     NEC特殊文字
 * 4 JIS X 0213 1面 IBM拡張文字
 * 5 JIS X 0213 2面 その他BMP
 * 6 JIS X 0212     追加面
 * 7 ベンダー外字   結合文字
 * 9 未定義         結合文字列
 * </pre>
 *
 * @author glad2121
 */
final class CharType implements Comparable<CharType> {

    /**
     * 未定義。
     */
    public static final CharType UNDEFINED = new CharType(-1);

    /**
     * 区分値と区分オブジェクトのマップ。
     */
    static final ConcurrentMap<Integer, CharType> map = new ConcurrentSkipListMap<>();

    /**
     * 区分値。
     */
    final int value;

    /**
     * コンストラクタ。
     *
     * @param value 区分値
     */
    private CharType(int value) {
        this.value = value;
    }

    /**
     * 区分値 value の文字区分を返します。
     *
     * @param value 区分値
     * @return 文字区分
     */
    public static CharType of(int value) {
        return map.getOrDefault(value, UNDEFINED);
    }

    /**
     * 区分値 value の文字区分があればそれを、なければ作成して返します。
     *
     * @param value 区分値
     * @return 文字区分
     */
    static CharType getOrCreate(int value) {
        return map.computeIfAbsent(value, CharType::new);
    }

    /**
     * このオブジェクトのハッシュ値を返します。
     */
    @Override
    public int hashCode() {
        return value;
    }

    /**
     * このオブジェクトと他のオブジェクトが等しいか判定します。
     */
    @Override
    public boolean equals(Object other) {
        if (!CharType.class.isInstance(other)) {
            return false;
        }
        return value == CharType.class.cast(other).value;
    }

    /**
     * このオブジェクトの文字列表現を返します。
     */
    @Override
    public String toString() {
        return String.format("%02X", value);
    }

    /**
     * このオブジェクトと他のオブジェクトの順序を比較します。
     */
    @Override
    public int compareTo(CharType o) {
        return Integer.compare(value, o.value);
    }

    /**
     * 11: ASCII (制御文字を除く) か判定します。
     */
    public boolean isAscii() {
        return value == 0x11;
    }

    /**
     * 2x: JIS X 0201 (ASCII との重複を除く) か判定します。
     */
    public boolean isJisX0201Ext() {
        return (value & 0xF0) == 0x20;
    }

    /**
     * 1x-2x: JIS X 0201 (ASCII を含む) か判定します。
     */
    public boolean isJisX0201() {
        return between(value & 0xF0, 0x10, 0x20);
    }

    /**
     * 3x: JIS X 0208 (非漢字、第1水準漢字、第2水準漢字) か判定します。
     */
    public boolean isJisX0208() {
        return (value & 0xF0) == 0x30;
    }

    /**
     * 4x: JIS X 0213 1面 (JIS X 0208 との重複を除く) か判定します。
     */
    public boolean isJisX0213P1Ext() {
        return (value & 0xF0) == 0x40;
    }

    /**
     * 3x-4x: JIS X 0213 1面 (JIS X 0208 を含む) か判定します。
     */
    public boolean isJisX0213P1() {
        return between(value & 0xF0, 0x30, 0x40);
    }

    /**
     * 5x: JIS X 0213 2面 か判定します。
     */
    public boolean isJisX0213P2() {
        return (value & 0xF0) == 0x50;
    }

    /**
     * 3x-5x: JIS X 0213 か判定します。
     */
    public boolean isJisX0213() {
        return between(value & 0xF0, 0x30, 0x50);
    }

    /**
     * x3: NEC特殊文字 か判定します。
     */
    public boolean isNecSpecialChar() {
        return (value & 0x0F) == 0x03;
    }

    /**
     * x4: IBM拡張文字 か判定します。
     */
    public boolean isIbmExt() {
        return (value & 0x0F) == 0x04;
    }

    /**
     * 1x-3x: JIS1990 (ASCII、JIS X 0201 を含む) か判定します。
     */
    public boolean isJis1990() {
        return between(value & 0xF0, 0x10, 0x30);
    }

    /**
     * 1x-5x: JIS2004 (ASCII、JIS X 0201 を含む) か判定します。
     */
    public boolean isJis2004() {
        return between(value & 0xF0, 0x10, 0x50);
    }

    /**
     * x1: BasicJ か判定します。
     */
    public boolean isBasicJ() {
        return (value & 0x0F) == 0x01;
    }

    /**
     * x2-x4: CommonJ か判定します。
     */
    public boolean isCommonJ() {
        return between(value & 0x0F, 0x01, 0x04);
    }

    /**
     * 指定された値が範囲内か判定します。
     *
     * @param value 評価する値
     * @param min 最小値
     * @param max 最大値
     * @return 範囲内なら {@code true}
     */
    static boolean between(int value, int min, int max) {
        return (min <= value) && (value <= max);
    }

}
