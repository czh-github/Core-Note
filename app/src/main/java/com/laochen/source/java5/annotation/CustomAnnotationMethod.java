package com.laochen.source.java5.annotation;

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
@Target(ElementType.METHOD)
public @interface CustomAnnotationMethod {
    public String author() default "danibuiza";
    public String date();
    public String description();
}
