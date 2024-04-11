package org.bmSpring.servlet;

import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;

public class HttpMethod {

    private MediaType mediaType;
    private HttpType httpType;
    private String controllerName;
    private String methodName;
    private String path;
    private Class<?> returnType;

    public HttpMethod(MediaType mediaType, HttpType httpType, String controllerName, String methodName, String path, Class<?> returnType) {
        this.mediaType = mediaType;
        this.httpType = httpType;
        this.controllerName = controllerName;
        this.methodName = methodName;
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

    public String getMethodName() {
        return methodName;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
