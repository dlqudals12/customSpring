package org.bmSpring.exception;

import org.bmSpring.exception.enums.BeanExceptionCode;

import java.io.Serial;

public class BeanClassLoadException extends BeanException {
    @Serial
    private static final long serialVersionUID = -673344853956132617L;

    public BeanClassLoadException() {
        super(BeanExceptionCode.CLASS_LOADER_ERROR);
    }
}
