package org.bmSpring.annotations.mapping;

import org.bmSpring.annotations.AliasFor;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(type = HttpType.POST)
public @interface PostMapping {
    @AliasFor(annotation = RequestMapping.class)
    String value() default "";

    @AliasFor(annotation = RequestMapping.class)
    MediaType contentType() default MediaType.APPLICATION_JSON_VALUE;

}
