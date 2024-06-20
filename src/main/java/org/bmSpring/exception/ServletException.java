package org.bmSpring.exception;

import org.bmSpring.exception.enums.ServletExceptionCode;

import java.io.Serial;

@SuppressWarnings("all")
public class ServletException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7609810042086457429L;
    private final ServletExceptionCode servletExceptionCode;

    public ServletException(ServletExceptionCode servletExceptionCode) {
        super(servletExceptionCode.getMsg());
        this.servletExceptionCode = servletExceptionCode;
    }

    public ServletExceptionCode getExceptionCode() {
        return servletExceptionCode;
    }
}
