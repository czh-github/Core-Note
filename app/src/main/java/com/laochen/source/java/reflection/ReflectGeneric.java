package com.laochen.source.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Date:2017/7/27 <p>
 * Author:chenzehao@danale.com <p>
 * Description:泛型的反射。Java的泛型信息是在编译时被擦除的，因此在运行时访问不到任何泛型信息，这并不完全正确。
 * 在少数情况下是能够在运行时访问泛型信息的。
 * 运行时无法知道一个类型本身被参数化成了何种类型：List不知道自己被参数化成了List<String>。
 * 但是能够在类型被使用和参数化的方法或字段里得到它的参数化类型：下面的demo。
 */

public class ReflectGeneric {
    private List<String> stringList;

    public List<String> getStringList(){
        return this.stringList;
    }

    public void setStringList(List<String> list){
        this.stringList = list;
    }

    public static void main(String[] args) throws Exception {
        // 获得泛型方法的返回类型的类型参数
        Method getStringList = ReflectGeneric.class.getMethod("getStringList");
        Type genericReturnType = getStringList.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericReturnType;
            Type[] actualTypeArguments = type.getActualTypeArguments();
            for (Type t : actualTypeArguments) {
                System.out.println(((Class)t).getName()); // java.lang.String
            }
        }

        // 获得泛型方法的参数的类型参数
        Method setStringList = ReflectGeneric.class.getMethod("setStringList", List.class);
        Type[] genericParameterTypes = setStringList.getGenericParameterTypes();
        for (Type type : genericParameterTypes) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                Type[] actualTypeArguments = pType.getActualTypeArguments();
                for (Type t : actualTypeArguments) {
                    System.out.println(((Class)t).getName()); // java.lang.String
                }
            }
        }

        // 获得泛型成员变量的类型参数
        Field stringList = ReflectGeneric.class.getDeclaredField("stringList");
        Type genericType = stringList.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = pType.getActualTypeArguments();
            for (Type t : actualTypeArguments) {
                System.out.println(((Class)t).getName()); // java.lang.String
            }
        }

    }
}
