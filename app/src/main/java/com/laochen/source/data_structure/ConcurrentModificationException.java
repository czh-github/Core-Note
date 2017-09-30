package com.laochen.source.data_structure;

/**
 * Date:2017/9/25 <p>
 * Author:chenzehao@danale.com <p>
 * Description:对象在不允许被修改的情况下被修改了，抛出该异常。
 *
 * 举个例子，当一个线程在迭代集合时，通常是不允许另外的线程修改这个集合的，因为这可能会导致迭代器的行为异常
 * （比如集合有5个元素，迭代最后一个元素之前，别的线程remove了一个元素，迭代就会出现问题）。
 * 某些迭代器的实现（包括JRE提供的一些）会抛出该异常如果发生上述情况。
 * 这样的迭代器称为fast-fail（快速失败）迭代器，防止将风险和不确定性扩散到以后。
 *
 * 除了别的线程修改不允许被修改的对象，会抛出该异常外，同一线程内，执行了违背该对象合约的方法，也会抛出该异常。
 * 举个例子，如果一个线程在迭代（fast-fail迭代器）一个集合时修改了该集合，也可以抛出该异常。
 *
 * fast-fail行为是无法得到保证的，因为不可能保证异步的并发修改一定会发生（根本原因是线程调度的不确定性）。
 * fast-fail行为尽最大努力抛出ConcurrentModificationException异常。
 * 因此，写程序时依赖ConcurrentModificationException很可能会出错，ConcurrentModificationException应该只被用来检测bug。
 *
 * 写程序不能依赖ConcurrentModificationException，是因为可能你的码代有问题，但因为线程调度的不确定性，问题没有显现出来，
 * 导致程序没有抛出ConcurrentModificationException，让你误认为程序没问题。
 *
 * ConcurrentModificationException应该只被用来检测bug，是说你应该多次测试你的代码，只要出现一次ConcurrentModificationException，
 * 就能说明代码逻辑是有问题的。
 *
 * 集合迭代器的这种fast-fail机制，在异步线程并发修改的情况下，可能抛出ConcurrentModificationException，虽然能够防止风险扩散，
 * 但同时程序也终止运行了，为了避免触发fast-fail机制，有两种解决办法（针对ArrayList）：
 * 1.读和写modCount的地方加锁同步。
 * 2.使用CopyOnWriteArrayList来替换ArrayList。推荐使用该方案。
 */

public class ConcurrentModificationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConcurrentModificationException() {
        super();
    }

    public ConcurrentModificationException(String message) {
        super(message);
    }

    public ConcurrentModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentModificationException(Throwable cause) {
        super(cause);
    }
}
