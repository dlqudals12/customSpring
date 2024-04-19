package org.bmSpring.util;

public class DefaultUtil {

    public static Object defaultValue(Class<?> type) {
        Object defaultValue = null;

        if (type == int.class) {
            defaultValue = 0;
        } else if (type == long.class) {
            defaultValue = 0L;
        } else if (type == float.class) {
            defaultValue = 0.0f;
        } else if (type == double.class) {
            defaultValue = 0.0;
        } else if (type == boolean.class) {
            defaultValue = Boolean.FALSE;
        } else if (type == char.class) {
            defaultValue = '\u0000';
        } else if (type == byte.class) {
            defaultValue = (byte) 0;
        } else if (type == short.class) {
            defaultValue = (short) 0;
        }

        return defaultValue;
    }
}
