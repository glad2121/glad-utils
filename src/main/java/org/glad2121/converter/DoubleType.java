package org.glad2121.converter;

class DoubleType extends ValueType {

    public DoubleType(Double defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Double convert(Object o) {
        return toDouble(o);
    }

    static Double toDouble(Object o) {
        if (o instanceof Number) {
            return toDouble((Number) o);
        }
        return toDouble(String.valueOf(o));
    }

    static Double toDouble(Number n) {
        if (n instanceof Double) {
            return (Double) n;
        }
        return n.doubleValue();
    }

    static double toDouble(String s) {
        return Double.parseDouble(s);
    }

}
