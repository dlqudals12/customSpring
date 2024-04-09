package org.bmSpring.servlet;

import org.bmSpring.servlet.enums.HttpType;

public class HttpMethod {

    private MediaType mediaType;
    private HttpType httpType;
    private String controllerName;
    private String methodName;

    public HttpMethod(MediaType mediaType, HttpType httpType, String controllerName, String methodName) {
        this.mediaType = mediaType;
        this.httpType = httpType;
        this.controllerName = controllerName;
        this.methodName = methodName;
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
}
