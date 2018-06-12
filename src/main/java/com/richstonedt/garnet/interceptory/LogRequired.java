package com.richstonedt.garnet.interceptory;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRequired {

    String module() default "";
    String method() default "";

}

