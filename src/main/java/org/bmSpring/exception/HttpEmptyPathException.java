package org.bmSpring.exception;

import org.bmSpring.exception.enums.ServletExceptionCode;

import java.io.Serial;

public class HttpEmptyPathException extends ServletException {

    @Serial
    private static final long serialVersionUID = 7466809138624125065L;

    public HttpEmptyPathException() {
        super(ServletExceptionCode.NOT_FOUND);
    }
}
