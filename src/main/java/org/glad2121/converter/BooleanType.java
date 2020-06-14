package org.glad2121.converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class BooleanType extends ValueType {

    static final Map<String, Boolean> STR_TO_BOOL;

    static {
        Map<String, Boolean> map = new HashMap<>();
        map.put("true", true);
        map.put("false", false);
        map.put("yes", true);
        map.put("no", false);
        map.put("on", true);
        map.put("off", false);
        map.put("1", true);
        map.put("0", false);
        STR_TO_BOOL = Collections.unmodifiableMap(map);
    }

    public BooleanType(Boolean defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean convert(Object o) {
        return toBoolean(o);
    }

    static Boolean toBoolean(Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof Character) {
            return toBoolean((char) o);
        }
        if (o instanceof Number) {
            return toBoolean((Number) o);
        }
        return toBoolean(o.toString());
    }

    static boolean toBoolean(char c) {
        if ("TtYy1".indexOf(c) >= 0) {
            return true;
        }
        if ("FfNn0".indexOf(c) >= 0) {
            return false;
        }
        throw new IllegalArgumentException(String.format("¥¥u%04X [%s]", c, c));
    }

    static boolean toBoolean(Number n) {
        return n.doubleValue() != 0;
    }

    static boolean toBoolean(String s) {
        Boolean b = STR_TO_BOOL.get(s.toLowerCase());
        if (b != null) {
            return b;
        }
        throw new IllegalArgumentException(s);
    }

}
