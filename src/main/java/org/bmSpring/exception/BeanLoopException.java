package org.bmSpring.exception;

import org.bmSpring.exception.enums.BeanExceptionCode;

import java.io.Serial;

public class BeanLoopException extends BeanException {

    @Serial
    private static final long serialVersionUID = 8381341077341348231L;

    public BeanLoopException() {
        super(BeanExceptionCode.BEAN_LOOP_ERROR);
    }
}
