package org.bmSpring.servlet;

import org.bmSpring.servlet.enums.HttpType;

import java.util.concurrent.ConcurrentHashMap;

public class HttpServletFactory {

    private final ConcurrentHashMap<String, String> mappings = new ConcurrentHashMap<>();

    public HttpServletFactory() {
//        Set<Class<?>> classes = ResourceLoader.classesContainsAnnotation(Controller.class);
//
//        for (Class<?> aClass : classes) {
//            RequestMapping request = aClass.getAnnotation(RequestMapping.class);
//
//            String path = "";
//
//            if (request != null) path = request.value();
//
//            Method[] methods = aClass.getMethods();
//
//            for (Method method : methods) {
//                RequestMapping methodRequest = null;
//
//                System.out.println("--------------------------------------------------------");
//                System.out.println(method.getName());
//                System.out.println(Arrays.toString(method.getAnnotations()));
//                System.out.println("--------------------------------------------------------");
//
//                for (Annotation annotation : method.getAnnotations()) {
//                    if (annotation.annotationType().isAnnotationPresent(RequestMapping.class))
//                        methodRequest = method.getAnnotation(RequestMapping.class);
//                }
//
//                if (methodRequest == null) continue;
//
//                String key = methodRequest.type().getValue() + path + methodRequest.value();
//
//                if (mappings.containsKey(key)) continue;
//
//                mappings.put(key, aClass.getSimpleName() + "/" + method.getName());
//            }
//        }
    }

    public ConcurrentHashMap<String, String> getMappings() {
        return mappings;
    }

    public String getBeanInfo(HttpType httpType, String path) {
        return mappings.get(httpType + path);
    }


}
