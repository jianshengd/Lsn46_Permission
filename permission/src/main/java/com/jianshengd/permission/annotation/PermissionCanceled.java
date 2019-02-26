package com.jianshengd.permission.annotation;


import com.jianshengd.permission.PermissionUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionCanceled {
    int requestCode() default PermissionUtils.DEFAULT_REQUEST_CODE;
}
