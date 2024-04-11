package org.bmSpring.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationUtil {

    public static String invokeString(Annotation annotation, String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(annotation, name, String.class);
    }

    public static <T> T invokeMethod(Annotation annotation, String name, Class<T> classType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method annotationMethod = annotation.annotationType().getMethod(name);
        Object invoke = annotationMethod.invoke(annotation);
        return classType.cast(invoke);
    }
}
