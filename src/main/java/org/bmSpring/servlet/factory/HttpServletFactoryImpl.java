package org.bmSpring.servlet.factory;

import com.sun.jdi.request.DuplicateRequestException;
import org.bmSpring.annotations.component.Controller;
import org.bmSpring.annotations.mapping.RequestMapping;
import org.bmSpring.annotations.parameter.RequestParam;
import org.bmSpring.annotations.parameter.ResponseBody;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;
import org.bmSpring.servlet.model.HttpMethod;
import org.bmSpring.servlet.model.HttpServletRequest;
import org.bmSpring.servlet.model.HttpServletResponse;
import org.bmSpring.util.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Set;

public class HttpServletFactoryImpl implements HttpServletFactory {

    private final HashMap<String, HttpMethod> mappings = new HashMap<>();

    public HttpServletFactoryImpl() {
        Set<Class<?>> classes = ResourceLoader.classesContainsAnnotation(Controller.class);

        for (Class<?> aClass : classes) {
            String beanName = aClass.getAnnotation(Controller.class).name();

            if (beanName.isEmpty()) beanName = aClass.getSimpleName();

            Method[] methods = aClass.getMethods();


            for (Method method : methods) {
                StringBuilder path = new StringBuilder();

                if (aClass.isAnnotationPresent(RequestMapping.class)) {
                    path = new StringBuilder(aClass.getAnnotation(RequestMapping.class).value());
                }

                Annotation[] annotations = method.getAnnotations();
                Parameter[] parameters = method.getParameters();

                HttpType httpType = null;
                MediaType content = null;

                try {
                    for (Annotation annotation : annotations) {
                        if (annotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
                            httpType = annotation.annotationType().getAnnotation(RequestMapping.class).type();
                            content = AnnotationUtil.invokeMethod(annotation, "contentType", MediaType.class);
                            path.append(AnnotationUtil.invokeString(annotation, "value"));
                        } else if (annotation.annotationType().equals(RequestMapping.class)) {
                            RequestMapping requestMapping = (RequestMapping) annotation;
                            httpType = requestMapping.type();
                            content = requestMapping.contentType();
                            path.append(requestMapping.value());
                        }
                    }

                    HashMap<String, Class<?>> requestType = new HashMap<>();
                    Class<?> bodyType = null;
                    boolean isRequest = false;
                    boolean isResponse = false;

                    for (Parameter parameter : parameters) {
                        if (parameter.isAnnotationPresent(RequestParam.class)) {
                            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);

                            String name = requestParam.name();

                            if (name.isEmpty()) name = parameter.getName();
                            if (requestParam.required()) name = "required/" + name;

                            requestType.put(name, parameter.getType());
                        } else if (parameter.isAnnotationPresent(ResponseBody.class)) {
                            ResponseBody responseBody = parameter.getAnnotation(ResponseBody.class);

                            bodyType = parameter.getType();
                        } else if (parameter.getType().equals(HttpServletRequest.class)) {
                            isRequest = true;
                        } else if (parameter.getType().equals(HttpServletResponse.class)) {
                            isResponse = true;
                        }


                    }

                    if (httpType != null && content != null && !path.isEmpty()) {
                        if (!path.toString().startsWith("/")) path.insert(0, "/");
                        
                        HttpMethod httpMethod = new HttpMethod(content, httpType, beanName, method, path.toString(),
                                method.getReturnType(), bodyType, requestType, isRequest, isResponse);

                        String key = httpType.getValue() + path;

                        if (mappings.containsKey(key)) throw new DuplicateRequestException();

                        mappings.put(key, httpMethod);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public HashMap<String, HttpMethod> getMappings() {
        return mappings;
    }

    public HttpMethod getHttpMethod(String key) {
        return mappings.get(key);
    }


}
