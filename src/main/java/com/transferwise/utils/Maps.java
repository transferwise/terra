package com.transferwise.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samsung on 05/05/2017.
 */
public class Maps {
    public static Map<String, Object> toMap(Object[] pairs) {
        if (pairs == null || pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Number of pairs should be different than null and even");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < pairs.length;) {
            map.put((String) pairs[i], pairs[i + 1]);
            i += 2;
        }

        return map;
    }

}
