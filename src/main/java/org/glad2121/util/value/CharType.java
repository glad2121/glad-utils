package org.glad2121.util.value;

class CharType extends ValueType {

    public CharType(Character defaultValue) {
        super(defaultValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Character convert(Object o) {
        return toCharacter(o);
    }

    static Character toCharacter(Object o) {
        if (o instanceof Character) {
            return (Character) o;
        }
        if (o instanceof Boolean) {
            return toChar((boolean) o);
        }
        if (o instanceof Number) {
            return toChar((Number) o);
        }
        return toChar(o.toString());
    }

    static char toChar(boolean b) {
        return b ? 'T': 'F';
    }

    static char toChar(Number n) {
        if (n instanceof Byte || n instanceof Short) {
            return (char) n.shortValue();
        }
        if (n instanceof Integer || n instanceof Long) {
            int c = IntType.toInteger(n);
            if (Short.MIN_VALUE <= c && c <= 0xFFFF) {
                return (char) c;
            }
        }
        throw new IllegalArgumentException(String.valueOf(n));
    }

    static char toChar(String s) {
        if (s.length() == 1) {
            return s.charAt(0);
        }
        throw new IllegalArgumentException(s);
    }

}
