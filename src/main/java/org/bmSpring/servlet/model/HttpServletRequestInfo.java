package org.bmSpring.servlet.model;

import lombok.Getter;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Object> requestParams;
    private boolean isHttpServletRequest;
    private boolean isHttpServletResponse;

    public void newRequest(HashMap<String, Object> headerMap) {
        String cookieString = (String) headerMap.get("cookie");
        if (cookieString != null && !cookieString.isEmpty()) {
            this.cookies = new ArrayList<>();

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
        this.remoteIp = header.containsKey("x-forwarded-for") ? header.get("x-forwarded-for").toString() : null;
        this.host = header.containsKey("host") ? header.get("host").toString() : null;
        this.acceptLanguage = header.containsKey("accept-language") ? header.get("accept-language").toString() : null;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
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
