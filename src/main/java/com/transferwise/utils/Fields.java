package com.transferwise.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samsung on 05/05/2017.
 */
public class Fields {
    public static Map<String, Field> extractFields(Class<?> clazz) {
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
