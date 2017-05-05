package com.transferwise.terra;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Terra {
    private static final Objenesis objenesis = new ObjenesisStd();

    public static <T> T hydrate(Class<?> clazz, Object... pairs) {
        return hydrate(clazz, toMap(pairs));
    }

    private static Map<String, Object> toMap(Object[] pairs) {
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Number of pairs should be even");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < pairs.length;) {
            map.put((String) pairs[i], pairs[i + 1]);
            i += 2;
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T> T hydrate(Class<?> clazz, Map<String, Object> properties) {
        ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clazz);
        T object = (T) instantiator.newInstance();

        Map<String, Field> fields = extractFields(clazz);
        for (Map.Entry<String, Object> entry: properties.entrySet()){
            String fieldName = entry.getKey();
            if (!fields.containsKey(fieldName)) {
                throw new IllegalArgumentException("Field " + fieldName + " does not exist in object");
            }

            Field f = fields.get(fieldName);
            f.setAccessible(true);
            try {
                f.set(object, entry.getValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return object;
    }

    private static Map<String, Field> extractFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();

        Class<?> i = clazz;
        while (i != null && i != Object.class) {
            for (Field f : i.getDeclaredFields()) {
                fields.put(f.getName(), f);
            }
            i = i.getSuperclass();
        }

        return fields;
    }
}
