package com.laochen.source.java5.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Date:2017/7/25 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

@CustomAnnotationClass(date = "2017/7/25")
public class AnnotatedClass {

    @CustomAnnotationMethod( date = "2014-06-05", description = "annotated method" )
    public String annotatedMethod()
    {
        return "annotatedMethod";
    }

    @CustomAnnotationMethod( author = "friend of mine", date = "2014-06-05", description = "annotated method" )
    public String annotatedMethodFromAFriend()
    {
        return "annotatedMethodFromAFriend";
    }

    @SafeVarargs
    public static <T> List<T> list(final T... items )
    {
        return Arrays.asList( items );
    }


    public static void main(String[] args) throws Exception {
        Annotation[] annotations = AnnotatedClass.class.getAnnotations();
        for (Annotation annotation : annotations) {
            // @com.laochen.jni.java5.annotation.CustomAnnotationClass(author=danibuiza, date=2017/7/25)
            System.out.println(annotation);
        }

        // 检查AnnotatedClass.class是否有CustomAnnotationClass类型的注解
        if (AnnotatedClass.class.isAnnotationPresent(CustomAnnotationClass.class)) {
            CustomAnnotationClass annotation = AnnotatedClass.class.getAnnotation(CustomAnnotationClass.class);
            // @com.laochen.jni.java5.annotation.CustomAnnotationClass(author=danibuiza, date=2017/7/25)
            System.out.println(annotation);
        }

        for (Method method : AnnotatedClass.class.getDeclaredMethods()) {
            // 检查method是否有CustomAnnotationMethod类型的注解
            if (method.isAnnotationPresent(CustomAnnotationMethod.class)) {
                CustomAnnotationMethod annotation = method.getAnnotation(CustomAnnotationMethod.class);
                System.out.println(annotation);
            }

        }

    }


}
