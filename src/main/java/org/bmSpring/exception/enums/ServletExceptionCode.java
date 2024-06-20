package org.bmSpring.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServletExceptionCode {
    NOT_FOUND(404, "NOT_FOUND"),
    SERVER_ERROR(500, "SERVER_ERROR"),
    REQUEST_ERROR(400, "REQUEST_ERROR");

    private final int code;
    private final String msg;
}
