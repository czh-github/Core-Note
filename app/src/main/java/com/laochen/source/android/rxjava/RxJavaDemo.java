package com.laochen.source.android.rxjava;

/**
 * Date:2017/8/16 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Observable：被观察的对象，负责"发射"值
 * Observer：观察者，订阅后，可以"接收"值
 * Observable正在发射对象，或者发生错误，或者完毕，会调用Observer的onNext()，onError()，onCompleted()
 */
public class RxJavaDemo {
    public static void main(String[] args) {

        f();

    }

    static void f() {
        Subscription subscription = Observable.just("Hello subscription")
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(Thread.currentThread().getName());
                        while (true) {
                            System.out.println(s);
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        System.out.println(subscription.isUnsubscribed());
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscription.unsubscribe();
        System.out.println(subscription.isUnsubscribed());
    }

    static void sync() {
        // 同步给Observable塞数据，如果没有Observer订阅，塞数据不会执行
        Observable<List<String>> listObservable = Observable.just(getColorList(), getColorList());
        // Observer订阅Observable
        listObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError()");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        System.out.println(Thread.currentThread().getName());
                        for (String s : strings) {
                            System.out.println(s);
                        }
                    }
                });
    }

    static void async() {
        Observable<List<String>> asyncObservable = Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return getColorList();
            }
        });
        asyncObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError()");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        System.out.println(Thread.currentThread().getName());
                        for (String s : strings) {
                            System.out.println(s);
                        }
                    }
                });
    }

    public static List<String> getColorList() {
        System.out.println(Thread.currentThread().getName());
        List<String> colorList = new ArrayList<>();
        colorList.add("RED");
        colorList.add("GREEN");
        colorList.add("BLUE");
        return colorList;
    }
}
