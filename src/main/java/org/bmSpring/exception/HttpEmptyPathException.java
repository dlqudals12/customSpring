package org.bmSpring.exception;

import org.bmSpring.exception.enums.ExceptionCode;

import java.io.Serial;

public class HttpEmptyPathException extends ServletException {

    @Serial
    private static final long serialVersionUID = 7466809138624125065L;

    public HttpEmptyPathException() {
        super(ExceptionCode.NOT_FOUND);
    }
}
