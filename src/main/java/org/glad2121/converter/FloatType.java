package org.glad2121.converter;

class FloatType extends ValueType {

    public FloatType(Float defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Float convert(Object o) {
        return toFloat(o);
    }

    static Float toFloat(Object o) {
        if (o instanceof Number) {
            return toFloat((Number) o);
        }
        if (o instanceof Boolean) {
            return toFloat((boolean) o);
        }
        if (o instanceof Character) {
            return toFloat((char) o);
        }
        return toFloat(o.toString());
    }

    static float toFloat(boolean b) {
        return IntType.toInt(b);
    }

    static float toFloat(char c) {
        return IntType.toInt(c);
    }

    static Float toFloat(Number n) {
        if (n instanceof Float) {
            return (Float) n;
        }
        return n.floatValue();
    }

    static float toFloat(String s) {
        return Float.parseFloat(s);
    }

}
