package com.laochen.jni.java7;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2017/8/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:自动推断泛型实例的类型
 */

public class TypeInterface {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(); // 尖括号无需重复声明类型参数
    }
}
