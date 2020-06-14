package org.glad2121.converter;

class DefaultValueType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Object o) {
        return (T) o;
    }

}
