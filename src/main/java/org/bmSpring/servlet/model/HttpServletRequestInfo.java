package org.bmSpring.servlet.model;

import lombok.Getter;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class HttpServletRequestInfo implements HttpServletRequest {
    private MediaType mediaType;
    private String controllerName;
    private Method method;
    private String path;
    private String remoteIp;
    private String host;
    private String acceptLanguage;
    private List<Cookie> cookies;
    private Object responseBody;
    private List<Object> requestParams;
    private boolean isHttpServletRequest;
    private boolean isHttpServletResponse;

    public void newRequest(HashMap<String, Object> headerMap) {
        if (headerMap.get("cookie") != null) {
            this.cookies = new ArrayList<>();

            String cookieString = (String) headerMap.get("cookie");

            String[] cookieSplit = cookieString.split("; ");

            for (String cookie : cookieSplit) {
                String[] keyValue = cookie.split("=");
                String key = keyValue[0];
                String value = keyValue[1];

                cookies.add(Cookie.builder()
                        .key(key)
                        .value(value)
                        .build());
            }
        }

        setHeaders(headerMap);
    }

    public HttpServletRequestInfo(HttpMethod httpMethod) {
        this.mediaType = httpMethod.getMediaType();
        this.controllerName = httpMethod.getControllerName();
        this.method = httpMethod.getMethod();
        this.path = httpMethod.getPath();
        this.isHttpServletRequest = httpMethod.isServletRequest();
        this.isHttpServletResponse = httpMethod.isServletResponse();
    }

    public void setHeaders(HashMap<String, Object> header) {
        this.remoteIp = header.get("x-forwarded-for").toString();
        this.host = header.get("host").toString();
        this.acceptLanguage = header.get("accept-language").toString();
    }

    public void setParameters(Object responseBody, List<Object> requestParam) {
        this.responseBody = responseBody;
        this.requestParams = requestParam;
    }

    @Override
    public String toString() {
        return "HttpServletRequestInfo{" +
                "mediaType=" + mediaType +
                ", controllerName='" + controllerName + '\'' +
                ", methodName='" + method + '\'' +
                ", path='" + path + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                ", host='" + host + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", cookies=" + cookies +
                '}';
    }


}
