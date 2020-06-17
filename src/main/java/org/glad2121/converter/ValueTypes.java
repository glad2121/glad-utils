package org.glad2121.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Timestamp;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 様々な値型を管理するクラス。
 *
 * @author glad2121
 */
class ValueTypes {

    /**
     * デフォルトの値型。
     */
    static final ValueType DEFAULT_VALUE_TYPE = new DefaultValueType();

    /**
     * データ型から値型へのマップ。
     */
    static final Map<Class<?>, ValueType> VALUE_TYPE_MAP;

    static {
        Map<Class<?>, ValueType> map = new IdentityHashMap<>();
        map.put(boolean.class, new BooleanType(false));
        map.put(Boolean.class, new BooleanType(null));
        map.put(char.class, new CharType('\0'));
        map.put(Character.class, new CharType(null));
        map.put(byte.class, new ByteType((byte) 0));
        map.put(Byte.class, new ByteType(null));
        map.put(short.class, new ShortType((short) 0));
        map.put(Short.class, new ShortType(null));
        map.put(int.class, new IntType(0));
        map.put(Integer.class, new IntType(null));
        map.put(long.class, new LongType(0L));
        map.put(Long.class, new LongType(null));
        map.put(float.class, new FloatType(0.0F));
        map.put(Float.class, new FloatType(null));
        map.put(double.class, new DoubleType(0.0));
        map.put(Double.class, new DoubleType(null));
        map.put(BigInteger.class, new BigIntegerType());
        map.put(BigDecimal.class, new BigDecimalType());
        map.put(Date.class, new DateType());
        map.put(Timestamp.class, new TimestampType());
        map.put(Instant.class, new InstantType());
        map.put(OffsetDateTime.class, new OffsetDateTimeType());
        map.put(ZonedDateTime.class, new ZonedDateTimeType());
        map.put(LocalDateTime.class, new LocalDateTimeType());
        map.put(LocalDate.class, new LocalDateType());
        map.put(String.class, new StringType());
        map.put(UUID.class, new UUIDType());
        VALUE_TYPE_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * 使用しないコンストラクタ。
     */
    private ValueTypes() {
    }

    /**
     * 指定されたデータ型の値型を返します。
     *
     * @param type データ型
     * @return 値型
     */
    static ValueType valueType(Class<?> type) {
        return VALUE_TYPE_MAP.getOrDefault(type, DEFAULT_VALUE_TYPE);
    }

}
