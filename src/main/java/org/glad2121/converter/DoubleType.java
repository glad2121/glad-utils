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
        if (o instanceof Boolean) {
            return toDouble((boolean) o);
        }
        if (o instanceof Character) {
            return toDouble((char) o);
        }
        return toDouble(o.toString());
    }

    static double toDouble(boolean b) {
        return IntType.toInt(b);
    }

    static double toDouble(char c) {
        return IntType.toInt(c);
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
