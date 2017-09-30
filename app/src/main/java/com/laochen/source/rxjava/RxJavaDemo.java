//package com.laochen.source.rxjava;
//
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.schedulers.Schedulers;
//
///**
// * http://gank.io/post/560e15be2dca930e00da1083
// * <p>
// * RxJava
// * Observable:被观察者
// * Observer:观察者
// * subscribe:订阅
// * event:事件
// * <p>
// * 例如Android中的点击事件监听： View 是被观察者，OnClickListener 是观察者，二者通过 setOnClickListener() 方法达成订阅关系。
// * 当用户点击时，OnClickListener 的 onClick() 方法会被调用，这样观察者就接收到了被观察者的点击事件。
// * <p>
// * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
// * <p>
// * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
// * <p>
// * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
// * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
// * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。
// */
//
//public class RxJavaDemo {
//    public static void main(String[] args) {
//
////        subscribe();
////        subscribePart();
//        scheduler();
//    }
//
//    /**
//     * Observer 和 Subscriber的区别对于使用者来说主要有两点：
//     * 1)onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
//     * 这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
//     * onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法。
//     * 2)unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。
//     * 一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
//     * Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方
//     * （例如 Activity#onPause()/onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
//     */
//    private static Observer<String> createObserver() {
//        // 创建Observer观察者
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("Error!");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
//            }
//        };
//
//        return observer;
//    }
//
//    /**
//     * 创建订阅者Subscriber
//     */
//    private static Subscriber<String> createSubscriber() {
//        // 订阅者Subscriber，实现了Observer接口和Subscription接口的抽象类
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("Error!");
//            }
//        };
//
//        return subscriber;
//    }
//
//    /**
//     * 创建被观察者Observable
//     * Observable决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则。
//     * create() 方法是 RxJava 最基本的创造事件序列的方法。
//     */
//    private static Observable<String> createObservable1() {
//        // 这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
//        // 当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发
//        // （对于上面的代码，就是观察者Subscriber 将会被调用三次 onNext() 和一次 onCompleted()）。
//        // 这样，由被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello");
//                subscriber.onNext("Hi");
//                subscriber.onNext("Aloha");
//                subscriber.onCompleted();
//            }
//        });
//        return observable;
//    }
//
//    /**
//     * 创建被观察者Observable
//     * just(T...)
//     * 最终都是调用Observable.create(Observable.OnSubscribe)
//     */
//    private static Observable<String> createObservable2() {
//        Observable<String> observable = Observable.just("Hello", "Hi", "Aloha");
//        // 将会依次调用：
//        // onNext("Hello");
//        // onNext("Hi");
//        // onNext("Aloha");
//        // onCompleted();
//        return observable;
//    }
//
//    /**
//     * 创建被观察者Observable
//     * from(T[]) / from(Iterable<? extends T>)
//     * 最终都是调用Observable.create(Observable.OnSubscribe)
//     */
//    private static Observable<String> createObservable3() {
//        Observable<String> observable = Observable.from(new String[]{"Hello", "Hi", "Aloha"});
//        // 将会依次调用：
//        // onNext("Hello");
//        // onNext("Hi");
//        // onNext("Aloha");
//        // onCompleted();
//        return observable;
//    }
//
//    /**
//     * 订阅subscribe：观察者/订阅者 订阅 被观察者
//     */
//    private static void subscribe() {
//        Observable<String> observable = createObservable1();
//        Observer<String> observer = createObserver();
//        //  Observer对象 在 subscribe() 过程中最终会被转换成 Subscriber 对象
//        observable.subscribe(observer);
//
//        // Observable.subscribe(Subscriber) 的内部实现是这样的（仅核心代码）：
//        // public Subscription subscribe(Subscriber subscriber) {
//        //    subscriber.onStart();
//        //    onSubscribe.call(subscriber);
//        //    return subscriber;
//        // }
////        subscribe() 做了3件事：
////        1.调用 Subscriber.onStart() 。这个方法在前面已经介绍过，是一个可选的准备方法。
////        2.调用 Observable 中的 OnSubscribe.call(Subscriber) 。在这里，事件发送的逻辑开始运行。从这也可以看出，
////          在 RxJava 中， Observable 并不是在创建的时候就立即开始发送事件，而是在它被订阅的时候，即当 subscribe() 方法执行的时候。
////        3.将传入的 Subscriber 作为 Subscription 返回。这是为了方便 unsubscribe().
//        Subscriber<String> subscriber = createSubscriber();
//        observable.subscribe(subscriber);
//    }
//
//    /**
//     * subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber。
//     * Action0接口只有一个方法 void call()，无参无返回值。可以包装onCompleted()作为一个参数传入 subscribe() 以实现不完整定义的回调。
//     * Action1接口只有一个方法 void call(T t)，单参数无返回值。可以包装onNext(T obj) 或 onError(Throwable error)作为一个参数传入 subscribe() 以实现不完整定义的回调。
//     * ActionX接口只有一个方法 void call(T t...)，X个参数无返回值。
//     */
//    private static void subscribePart() {
//        Observable<String> observable = createObservable1();
//        Action1<String> onNextAction = new Action1<String>() {
//            // onNext()
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        };
//        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
//            // onError()
//            @Override
//            public void call(Throwable throwable) {
//                // Error handling
//                System.out.println(throwable.getMessage());
//            }
//        };
//        Action0 onCompletedAction = new Action0() {
//            // onCompleted()
//            @Override
//            public void call() {
//                System.out.println("Completed!");
//            }
//        };
//
//        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
//        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction, onErrorAction);
//        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
//        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
//    }
//
//    /**
//     * 在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。也就是说，如果只用上面的方法，实现出来的只是一个同步的观察者模式。
//     * 观察者模式本身的目的就是『后台处理，前台回调』的异步机制，因此异步对于 RxJava 是至关重要的。
//     * 而要实现异步，则需要用到 RxJava 的另一个概念： Scheduler 。
//     * <p>
//     * 在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe()，就在哪个线程生产事件；
//     * 在哪个线程生产事件，就在哪个线程消费事件(OnSubscribe.call(Subscriber))。如果需要切换线程，就需要用到 Scheduler.
//     * <p>
//     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
//     * <p>
//     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
//     * <p>
//     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
//     * 区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
//     * 不要把计算工作放在 io() 中，可以避免创建不必要的线程。
//     * <p>
//     * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
//     * 这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
//     * <p>
//     * Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
//     * <p>
//     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程，或者叫做事件产生的线程。
//     * observeOn(): 指定 Subscriber 所运行在的线程，或者叫做事件消费的线程。
//     */
//    public static void scheduler() {
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                // io线程
//                System.out.println(Thread.currentThread().getName());
//                subscriber.onNext("Hello");
//                subscriber.onNext("Hi");
//                subscriber.onNext("Aloha");
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(Schedulers.newThread()) // 指定 Subscriber 的回调发生的线程
//                .subscribe(new Action1<String>() { // new Action1会被包装成Subscriber，Subscriber#onNext(T)调用Action1#call(T)
//                    @Override
//                    public void call(String s) {
//                        // newThread
//                        System.out.println(Thread.currentThread().getName());
//                        System.out.println(s);
//                    }
//                });
//    }
//}
