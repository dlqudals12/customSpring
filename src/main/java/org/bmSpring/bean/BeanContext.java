package org.bmSpring.bean;

import lombok.Getter;
import org.bmSpring.annotations.Qualifier;
import org.bmSpring.annotations.bean.Autowired;
import org.bmSpring.annotations.bean.Bean;
import org.bmSpring.annotations.component.Component;
import org.bmSpring.annotations.component.Configuration;
import org.bmSpring.annotations.component.Controller;
import org.bmSpring.exception.BeanClassLoadException;
import org.bmSpring.exception.BeanCreateException;
import org.bmSpring.scan.ResourceLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;


@Getter
public class BeanContext {

    private final HashMap<String, Object> beans = new HashMap<>();

    public BeanContext() {
        Set<Class<?>> classBeans = new HashSet<>();
        List<BeanMethod> beanMethods = new ArrayList<>();

        try {
            Set<Class<?>> componentClasses = ResourceLoader.classesContainsAnnotation(Component.class);


            for (Class<?> componentClass : componentClasses) {
                if (componentClass.isAnnotationPresent(Configuration.class)) {
                    Method[] methods = componentClass.getMethods();
                    String configName = componentClass.getAnnotation(Configuration.class).name();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Bean.class)) {
                            String name = method.getAnnotation(Bean.class).name();

                            if (name.isEmpty()) name = method.getName();

                            beanMethods.add(new BeanMethod(configName.isEmpty() ? componentClass.getSimpleName() : configName, name, method, method.getReturnType()));
                        }
                    }
                }

                classBeans.add(componentClass);
            }
        } catch (Exception e) {
            throw new BeanClassLoadException();
        }

        //bean 의존성 자동 주입
        for (BeanMethod beanMethod : beanMethods) {
            if (beans.containsKey(beanMethod.getBeanName())) continue;

            putBeans(beanMethods, beanMethod, classBeans);
        }

        classBeans.forEach(this::putBeans);
    }

    /**
     * Component annotation bean 주입
     *
     * @param beanClass
     */
    private void putBeans(Class<?> beanClass) {
        String name = "";

        if (beanClass.isAnnotationPresent(Component.class))
            name = beanClass.getAnnotation(Component.class).name();
        else if (beanClass.isAnnotationPresent(Configuration.class))
            name = beanClass.getAnnotation(Configuration.class).name();
        else if (beanClass.isAnnotationPresent(Controller.class))
            name = beanClass.getAnnotation(Controller.class).name();

        if (name.isEmpty()) name = beanClass.getSimpleName();

        if (beans.get(name) == null) {
            try {
                Constructor<?> constructor = beanClass.getDeclaredConstructors()[0];
                Class<?>[] parameterTypes = constructor.getParameterTypes();

                //roof
                for (Class<?> parameterType : parameterTypes) {
                    if (!beans.containsKey(parameterType.getSimpleName())) putBeans(parameterType);
                }

                Object bean = constructor.newInstance(Arrays.stream(parameterTypes).map(pa -> beans.get(pa.getSimpleName())).toArray());

                beans.put(name, bean);
            } catch (Exception e) {
                throw new BeanCreateException();
            }
        }
    }

    /**
     * Bean annotation으로 설정되어 있는 method bean 주입
     *
     * @param beanMethods
     * @param beanMethod
     * @param classBeans
     */
    private void putBeans(List<BeanMethod> beanMethods, BeanMethod beanMethod, Set<Class<?>> classBeans) {

        //등록하려는 bean의 class가 등록되어 있지 않으면 class주입
        if (beans.get(beanMethod.getClassName()) == null)
            putBeans(classBeans.stream().filter(c -> c.getSimpleName().equals(beanMethod.getClassName())).findFirst().orElseThrow(NullPointerException::new));

        Method method = beanMethod.getMethod();

        Parameter[] parameters = method.getParameters();

        try {
            List<String> parameterBeanNames = new ArrayList<>();

            for (Parameter parameter : parameters) {
                String parameterBeanName;

                if (parameter.isAnnotationPresent(Qualifier.class))
                    parameterBeanName = parameter.getAnnotation(Qualifier.class).value();
                else parameterBeanName = parameter.getName();

                if (!beans.containsKey(parameterBeanName)) {
                    BeanMethod filterMethod = beanMethods.stream().filter(parameterMethod -> parameterMethod.getBeanName().equals(parameterBeanName)).findFirst().orElseThrow(NullPointerException::new);

                    putBeans(beanMethods, filterMethod, classBeans);
                } else parameterBeanNames.add(parameterBeanName);
            }

            Object bean;

            if (parameterBeanNames.isEmpty())
                bean = method.invoke(beans.get(beanMethod.getClassName()));
            else
                bean = method.invoke(beans.get(beanMethod.getClassName()), parameterBeanNames.stream().map(beans::get).toList());

            Stream.of(bean.getClass().getFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class) && !Modifier.isFinal(field.getModifiers()))
                    .forEach(field -> {
                        field.setAccessible(true);

                        try {
                            field.set(bean, beans.get(field.getName()));
                        } catch (IllegalAccessException e) {
                            throw new BeanCreateException();
                        }
                    });

            beans.put(beanMethod.getBeanName(), bean);
        } catch (Exception e) {
            throw new BeanCreateException();
        }
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
}
