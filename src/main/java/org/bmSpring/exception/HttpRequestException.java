package org.bmSpring.exception;

import org.bmSpring.exception.enums.ExceptionCode;

import java.io.Serial;

public class HttpRequestException extends ServletException {
    @Serial
    private static final long serialVersionUID = 3971421693561209559L;

    public HttpRequestException() {
        super(ExceptionCode.REQUEST_ERROR);
    }
}
