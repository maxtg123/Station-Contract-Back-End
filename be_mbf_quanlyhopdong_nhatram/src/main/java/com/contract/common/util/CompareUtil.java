package com.contract.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CompareUtil {
    public static Map<String, Object> getDiff(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            return null;
        }
        Class<?> clazz = obj1.getClass();
        Map<String, Object> diff = new HashMap<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value1 = field.get(obj1);
                Object value2 = field.get(obj2);

                if ((value1 != null && !value1.equals(value2)) || (value2 != null && !value2.equals(value1))) {
                    diff.put(field.getName(), value2);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return diff;
    }
}
