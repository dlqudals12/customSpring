package org.bmSpring.servlet.model;

import org.bmSpring.servlet.enums.MediaType;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public interface HttpServletResponse {

    PrintWriter getPrintWriter();

    String getStatus();

    MediaType getContentType();

    HashMap<String, Object> getHeader();

    List<Cookie> getCookies();

    void setCookies(List<Cookie> cookies);

    void setContentType(MediaType contentType);
}
