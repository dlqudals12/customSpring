package org.bmSpring.exception;

import org.bmSpring.exception.enums.ExceptionCode;

import java.io.Serial;

public class HttpServerException extends ServletException {
    @Serial
    private static final long serialVersionUID = -5844641407083516190L;

    public HttpServerException() {
        super(ExceptionCode.SERVER_ERROR);
    }
}
