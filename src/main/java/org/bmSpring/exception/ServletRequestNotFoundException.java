package org.bmSpring.exception;

import java.io.Serial;

public class ServletRequestNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3971421693561209559L;

    public ServletRequestNotFoundException() {
        super("Servlet not match path");
    }

    public ServletRequestNotFoundException(String message) {
        super(message);
    }

    public ServletRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
