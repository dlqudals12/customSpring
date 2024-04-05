package org.bmSpring.bean;

import org.bmSpring.annotations.Bean;
import org.bmSpring.scan.ResourceLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BeanCreate {

    private final HashMap<String, Object> beans = new HashMap<>();

    public BeanCreate() {
        HashMap<Class<?>, Class<? extends Annotation>> beanClasses = ResourceLoader.classesContainsAnnotation(Bean.class);

        for (Map.Entry<Class<?>, Class<? extends Annotation>> beanClass : beanClasses.entrySet()) {
            putBeans(beanClass.getKey(), beanClass.getValue());
        }

    }

    public void putBeans(Class<?> beanClass, Class<? extends Annotation> ano) {
        Bean annotation = (Bean) beanClass.getAnnotation(ano);

        String name = annotation.name();

        if (name.isEmpty()) name = beanClass.getSimpleName();

        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            for (Class<?> parameterType : parameterTypes) {
                if (!beans.containsKey(parameterType.getSimpleName())) putBeans(parameterType, ano);
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
