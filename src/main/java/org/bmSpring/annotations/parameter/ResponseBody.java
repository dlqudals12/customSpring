package org.bmSpring.annotations.parameter;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {
    boolean required() default true;
}
