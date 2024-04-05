package org.bmSpring.annotations.component;

import org.bmSpring.annotations.AliasFor;
import org.bmSpring.annotations.Bean;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Bean
public @interface Component {

    @AliasFor(annotation = Bean.class)
    String name() default "";
    
}
