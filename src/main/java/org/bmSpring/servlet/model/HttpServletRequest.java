package org.bmSpring.servlet.model;

import org.bmSpring.servlet.enums.MediaType;

import java.lang.reflect.Method;
import java.util.List;

public interface HttpServletRequest {
    MediaType getMediaType();

    String getControllerName();

    Method getMethod();

    String getPath();

    String getRemoteIp();

    String getHost();

    String getAcceptLanguage();

    List<Cookie> getCookies();
}
