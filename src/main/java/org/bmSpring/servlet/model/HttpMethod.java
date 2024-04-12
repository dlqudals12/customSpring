package org.bmSpring.servlet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.reflect.Method;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class HttpMethod {

    private MediaType mediaType;
    private HttpType httpType;
    private String controllerName;
    private Method method;
    private String path;
    private Class<?> returnType;
    private Class<?> bodyType;
    private HashMap<String, Class<?>> requestParam;
    private boolean isServletRequest;
    private boolean isServletResponse;

    public HttpMethod(MediaType mediaType, HttpType httpType, String controllerName, Method method, String path, Class<?> returnType) {
        this.mediaType = mediaType;
        this.httpType = httpType;
        this.controllerName = controllerName;
        this.method = method;
        this.path = path;
        this.returnType = returnType;
    }
}
