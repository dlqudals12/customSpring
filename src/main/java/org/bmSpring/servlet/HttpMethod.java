package org.bmSpring.servlet;

import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.reflect.Method;

public class HttpMethod {

    private MediaType mediaType;
    private HttpType httpType;
    private String controllerName;
    private Method method;
    private String path;
    private Class<?> returnType;

    public HttpMethod(MediaType mediaType, HttpType httpType, String controllerName, Method method, String path, Class<?> returnType) {
        this.mediaType = mediaType;
        this.httpType = httpType;
        this.controllerName = controllerName;
        this.method = method;
        this.path = path;
        this.returnType = returnType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public String getControllerName() {
        return controllerName;
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
