package com.laochen.source.data_structure;

/**
 * Date:2017/9/26 <p>
 * Author:chenzehao@danale.com <p>
 * Description:随机访问接口
 *
 * 是一个标记接口，用于{@link java.util.List}的实现类，表明它支持随机访问（一般指的是访问任意元素花费相同的常量时间，
 * 例如List的数组实现ArrayList，数组就是一种支持随机访问的结构，因为数组元素是紧密排列在内存的，通过index可以访问任意元素）。
 *
 * 该接口的作用是告诉程序，遍历这类集合时，可以采用更高效的下标遍历方式：
 * <pre>
 * for (int i = 0; i < size; i++) {
 *     list.get(i);
 * }
 * </pre>
 */

public interface RandomAccess {
}
