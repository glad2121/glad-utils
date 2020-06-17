package org.glad2121.util.value;

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
        return toFloat(String.valueOf(o));
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
