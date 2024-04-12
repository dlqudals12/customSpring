package org.bmSpring.annotations.parameter;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String name() default "";

    boolean required() default true;
}
