package org.bmSpring.servlet.model;

import lombok.Getter;
import org.bmSpring.servlet.enums.MediaType;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@Getter
public class HttpServletResponseInfo implements HttpServletResponse {

    private final PrintWriter printWriter;
    private String status;
    private MediaType contentType;
    private HashMap<String, Object> header;
    private List<Cookie> cookies;

    public HttpServletResponseInfo(PrintWriter out) {
        this.printWriter = out;
    }

    public HttpServletResponseInfo(PrintWriter out, HttpMethod httpMethod, HashMap<String, Object> headers, List<Cookie> cookies) {
        this.printWriter = out;
        this.contentType = httpMethod.getMediaType();
        this.header = headers;
        this.cookies = cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }
}
