package com.annotation;

/**
 * created by Aylan
 * on 2020/12/2 8:46 下午
 */
public @interface Database {
    boolean readOnly() default true;
}
