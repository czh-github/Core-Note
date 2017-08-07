package com.laochen.source.java7;

/**
 * Date:2017/8/1 <p>
 * Author:chenzehao@danale.com <p>
 * Description:在一个catch块中捕获多类异常，多类异常不能相交（不能是继承关系）
 */

public class MultiCatch {
    public static void main(String args[]){
        try{
            int array[] = new int[10];
            array[10] = 30/0;
        }
        catch(ArithmeticException | ArrayIndexOutOfBoundsException e){ // e是final的
            System.out.println(e.getMessage());
        }
    }
}
