package org.bmSpring.exception;

import org.bmSpring.exception.enums.ExceptionCode;

import java.io.Serial;

@SuppressWarnings("all")
public class ServletException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7609810042086457429L;
    private final ExceptionCode exceptionCode;

    public ServletException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMsg());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
