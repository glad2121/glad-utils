package org.glad2121.charset;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 追加の文字集合を提供するプロバイダ。
 *
 * @author glad2121
 */
public class CharsetProvider extends java.nio.charset.spi.CharsetProvider {

    /**
     * 提供する文字集合。
     */
    final List<Charset> charsets;

    /**
     *
     */
    final Map<String, Charset> nameToCharset;

    /**
     * コンストラクタ。
     */
    public CharsetProvider() {
        charsets = Collections.unmodifiableList(Arrays.asList(
            new ShiftJISg(),
            new ShiftJIS2004g(),
            new Windows31Jg(),
            new Windows31J2004g()
        ));
        Map<String, Charset> map = new HashMap<>();
        for (Charset charset : charsets) {
            map.put(charset.name(), charset);
            charset.aliases().forEach(name -> map.put(name, charset));
        }
        nameToCharset = Collections.unmodifiableMap(map);
    }

    /**
     * 提供する文字集合のイテレータを返します。
     */
    @Override
    public Iterator<Charset> charsets() {
        return charsets.iterator();
    }

    /**
     * 指定された名前の文字集合を返します。
     */
    @Override
    public Charset charsetForName(String charsetName) {
        return nameToCharset.get(charsetName);
    }

}
