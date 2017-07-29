package com.laochen.jni.java5.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date:2017/7/25 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomAnnotationClass {
    public String author() default "CustomAnnotationClass author";
    public String date();
}
