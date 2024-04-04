package org.bmSpring.annotations;

import org.bmSpring.servlet.MediaType;
import org.bmSpring.servlet.enums.HttpType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(type = HttpType.PATCH)
public @interface PatchMapping {
    @AliasFor(annotation = RequestMapping.class)
    String value() default "";

    @AliasFor(annotation = RequestMapping.class)
    String contentType() default MediaType.APPLICATION_JSON_VALUE;

}
