package org.bmSpring;

import org.bmSpring.container.CustomApplication;

public class Main {

    public static void main(String[] args) {
        CustomApplication.run(Main.class, args);

//        Reflections reflections = new Reflections("org.bmSpring", new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner());
//        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);
//
//        for (Class<?> aClass : typesAnnotatedWith) {
//            if (aClass.isAnnotation()) continue;
//            Method[] methods = aClass.getMethods();
//
//            for (Method method : methods) {
//                Annotation[] annotations = method.getAnnotations();
//
//                for (Annotation annotation : annotations) {
//                    System.out.println(annotation.annotationType());
//                    if (annotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
//
//                        RequestMapping annotation1 = annotation.annotationType().getAnnotation(RequestMapping.class);
//
//                        HttpType type = annotation1.type();
//                        System.out.println(type);
//
//
//                    }
//                }
//            }
//        }
    }

}