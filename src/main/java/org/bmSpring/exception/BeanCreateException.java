package org.bmSpring.exception;

import org.bmSpring.exception.enums.BeanExceptionCode;

import java.io.Serial;

public class BeanCreateException extends BeanException {


    @Serial
    private static final long serialVersionUID = -6601389671809233500L;

    public BeanCreateException() {
        super(BeanExceptionCode.BEAN_CREATE_ERROR);
    }
}
