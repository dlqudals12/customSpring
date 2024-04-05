package org.bmSpring.annotations.mapping;

import org.bmSpring.annotations.AliasFor;
import org.bmSpring.servlet.MediaType;
import org.bmSpring.servlet.enums.HttpType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(type = HttpType.GET)
public @interface GetMapping {

    @AliasFor(annotation = RequestMapping.class)
    String value() default "";

    @AliasFor(annotation = RequestMapping.class)
    String contentType() default MediaType.APPLICATION_JSON_VALUE;

}
