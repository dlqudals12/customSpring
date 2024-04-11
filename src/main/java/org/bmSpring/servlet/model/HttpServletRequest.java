package org.bmSpring.servlet.model;

import org.bmSpring.servlet.HttpMethod;
import org.bmSpring.servlet.enums.MediaType;

import java.util.HashMap;

public class HttpServletRequest {
    private MediaType mediaType;
    private String controllerName;
    private String methodName;
    private String path;
    private String remoteIp;
    private String host;
    private String acceptLanguage;
    private HashMap<String, Object> cookies;

    public void newRequest(HashMap<String, Object> headerMap) {
        if (headerMap.get("cookie") != null) {
            HashMap<String, Object> cookieMap = new HashMap<>();
            String cookies = (String) headerMap.get("cookie");

            String[] cookieSplit = cookies.split("; ");

            for (String cookie : cookieSplit) {
                String[] keyValue = cookie.split("=");

                cookieMap.put(keyValue[0], keyValue[1]);
            }

            setCookies(cookieMap);
        }

        setHeaders(headerMap);
    }

    public HttpServletRequest(HttpMethod httpMethod) {
        this.mediaType = httpMethod.getMediaType();
        this.controllerName = httpMethod.getControllerName();
        this.methodName = httpMethod.getMethodName();
        this.path = httpMethod.getPath();
    }

    public void setHeaders(HashMap<String, Object> header) {
        this.remoteIp = header.get("x-forwarded-for").toString();
        this.host = header.get("host").toString();
        this.acceptLanguage = header.get("accept-language").toString();
    }

    public void setCookies(HashMap<String, Object> cookies) {
        this.cookies = cookies;
    }


    public MediaType getMediaType() {
        return mediaType;
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


    @Override
    public String toString() {
        return "HttpServletRequest{" +
                "mediaType=" + mediaType +
                ", controllerName='" + controllerName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", path='" + path + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                ", host='" + host + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", cookies=" + cookies +
                '}';
    }
}
