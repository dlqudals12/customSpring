package org.bmSpring.exception;

import org.bmSpring.exception.enums.BeanExceptionCode;

import java.io.Serial;

public class BeanException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1020084016126913529L;
    private final BeanExceptionCode beanExceptionCode;

    public BeanException(BeanExceptionCode beanExceptionCode) {
        super(beanExceptionCode.getMsg());
        this.beanExceptionCode = beanExceptionCode;
    }

    public BeanExceptionCode getExceptionCode() {
        return beanExceptionCode;
    }
}
