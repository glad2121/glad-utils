package org.glad2121.converter;

import java.util.UUID;

class UUIDType extends ValueType {

    @Override
    @SuppressWarnings("unchecked")
    public UUID convert(Object o) {
        return toUUID(o);
    }

    static UUID toUUID(Object o) {
        if (o instanceof UUID) {
            return (UUID) o;
        }
        return toUUID(o.toString());
    }

    static UUID toUUID(String s) {
        return UUID.fromString(s);
    }

}
