package org.bmSpring.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BeanExceptionCode {
    BEAN_CREATE_ERROR("Bean create error"),
    BEAN_LOOP_ERROR("Bean loop error"),
    CLASS_LOADER_ERROR("Class load error");

    private final String msg;
}
