package com.sardor.unsplash.annotations;

import java.lang.annotation.*;



@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    String value();
}
