package org.bmSpring.annotations.mapping;

import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    HttpType type() default HttpType.CLASS;

    String value() default "";

    MediaType contentType() default MediaType.APPLICATION_JSON_VALUE;
}
