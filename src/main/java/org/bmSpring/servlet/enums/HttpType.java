package org.bmSpring.servlet.enums;

public enum HttpType {
    GET("get/"),
    POST("post/"),
    PUT("put/"),
    PATCH("patch/"),
    DELETE("delete/"),
    CLASS("");

    private final String value;

    HttpType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
