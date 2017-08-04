package com.laochen.jni.java7;

/**
 * Date:2017/8/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:String常量作为case后面的参数
 */

public class SwitchWithString {
    public static void main(String[] args) {
        String game = "Cricket"; // game的声明类型只能是String，不能是Object
        switch (game) { // 大小写敏感
            case "Hockey":
                System.out.println("Let's play Hockey");
                break;
            case "Cricket":
                System.out.println("Let's play Cricket");
                break;
            case "Football":
                System.out.println("Let's play Football");
        }
    }
}
