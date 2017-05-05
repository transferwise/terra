package com.transferwise.terra;

import com.transferwise.utils.Fields;
import com.transferwise.utils.Maps;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import java.lang.reflect.Field;
import java.util.Map;

public class Terra {
    private static final Objenesis objenesis = new ObjenesisStd();

    public static <T> T hydrate(Class<?> clazz, Object... pairs) {
        return hydrate(clazz, Maps.toMap(pairs));
    }

    @SuppressWarnings("unchecked")
    public static <T> T hydrate(Class<?> clazz, Map<String, Object> properties) {
        ObjectInstantiator instantiator = objenesis.getInstantiatorOf(clazz);
        T object = (T) instantiator.newInstance();

        Map<String, Field> fields = Fields.extractFields(clazz);
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

}
