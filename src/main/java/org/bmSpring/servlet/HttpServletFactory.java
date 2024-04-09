package org.bmSpring.servlet;

import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.RequestMapping;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.enums.HttpType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HttpServletFactory {

    private final HashMap<HttpType, Set<String>> mappings = new HashMap<>();
    private final Set<HttpMethod> httpMethods = new HashSet<>();

    public HttpServletFactory() {
        Set<Class<?>> classes = ResourceLoader.classesContainsAnnotation(Controller.class);

        for (Class<?> aClass : classes) {
            String path = "";
            String beanName = aClass.getAnnotation(Controller.class).name();

            if (beanName.isEmpty()) beanName = aClass.getSimpleName();

            if (aClass.isAnnotationPresent(RequestMapping.class))
                path = aClass.getAnnotation(RequestMapping.class).value();

            for (Method method : aClass.getMethods()) {

            }
        }
    }

    public HashMap<HttpType, Set<String>> getMappings() {
        return mappings;
    }


}
