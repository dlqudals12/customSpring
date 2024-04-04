package org.bmSpring.bean;

import org.bmSpring.annotations.Bean;
import org.bmSpring.scan.ResourceLoader;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


public class BeanCreate {

    private final HashMap<String, Object> beans = new HashMap<>();

    public BeanCreate() {
        Set<Class<?>> beanClasses = ResourceLoader.classesByAnnotation(Bean.class);

        for (Class<?> beanClass : beanClasses) {
            if (beans.containsKey(beanClass.getName()) || beanClass.getAnnotation(Bean.class) == null) continue;

            putBeans(beanClass);
        }
    }

    public void putBeans(Class<?> beanClass) {
        Bean annotation = beanClass.getAnnotation(Bean.class);

        String name = annotation.name();

        if (name.isEmpty()) name = beanClass.getSimpleName();

        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            for (Class<?> parameterType : parameterTypes) {
                if (!beans.containsKey(parameterType.getSimpleName())) putBeans(parameterType);
            }
            Object bean = constructor.newInstance(Arrays.stream(parameterTypes).map(pa -> beans.get(pa.getSimpleName())).toArray());

            beans.put(name, bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Object> getBeans() {
        return beans;
    }
}
