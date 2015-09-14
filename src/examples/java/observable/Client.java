package observable;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by frodo on 2015/9/11. for test
 */
public class Client {
    public static Observable<String> createObservable() {
        return Observable
                .create(new Observable.OnSubscribe<String>() {

                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("start element");
                        subscriber.onNext("one");
                        subscriber.onNext("two");
                        subscriber.onNext("three");
                        subscriber.onNext("four");
                        subscriber.onNext("five");
                        subscriber.onNext("one");
                        subscriber.onNext("two");
                        subscriber.onNext("end element");
                        subscriber.onCompleted();
                    }

                });
    }

    public static Subscription observableSubscribe(Observable<? extends Object> observable) {
        return observable.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                flag = false;
                Utils.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Utils.println("onError : " + e.getMessage());
            }

            @Override
            public void onNext(Object s) {
                Utils.println("onNext : " + s);
            }
        });
    }

    private static void multiOperation() {
        Subscription subscription = Observable.just("url001", "url002-1", "url002-2", "url002-3", "url002-4", "url003")
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        return Observable.just("flatMap 1 -> " + s);
                    }
                })
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return "map call : " + o;
                    }
                })
                .flatMap(new Func1<Object, Observable<?>>() {
                    @Override
                    public Observable<?> call(Object o) {
                        return Observable.just("flatMap 2 -> " + o);
                    }
                })
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return o.toString().contains("url002");
                    }
                })
                .take(3)
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Utils.println("doOnNext :" + o);
                    }
                })
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        Utils.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.println("onError : " + e.getMessage());
                    }

                    @Override
                    public void onNext(Object s) {
                        Utils.println("onNext : " + s);
                    }
                });
        Utils.println("before unsubscribed : " + subscription.isUnsubscribed());
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        Utils.println("after unsubscribed : " + subscription.isUnsubscribed());
    }

    public static volatile boolean flag = true;

    public static void main(String[] args) {
        Utils.println("==== start ====");

        //        Create.createMethod();
        //        Create.deferMethod();
        //        Create.emptyNeverThrowMethod();
        //        Create.fromMethod();
        //        Create.justMethod();
        //        Create.rangeMethod();
        //        Create.timerMethod();
        //        Create.intervalMethod();

        //        Transform.bufferMethod();
        //        Transform.flatMapMethod();
        //        Transform.groupByMethod();
        //        Transform.mapMethod();
        //        Transform.scanMethod();
        //        Transform.windowMethod();

        //        Filter.debounceMethod();
        //        Filter.distinctMethod();
        //        Filter.elementAtMethod();
        //        Filter.filterMethod();
        //        Filter.firstMethod();
        //        Filter.ignoreElementsMethod();
        //        Filter.lastMethod();
        //        Filter.sampleMethod();
        //        Filter.skipMethod();
        //        Filter.skipLastMethod();
        //        Filter.takeMethod();
        //        Filter.takeLastMethod();

        //        Combine.joinMethod();
        //        Combine.mergeMethod();
        //        Combine.startWithMethod();
        //        Combine.switchIfEmptyMethod();
        //        Combine.zipWithMethod();

        //        ErrorHandler.onErrorReturnMethod();
        //        ErrorHandler.onErrorResumeNextMethod();
        //        ErrorHandler.onExceptionResumeNextMethod();
        //        ErrorHandler.retryMethod();

        Utility.delayMethod();

        Utils.println("==== waiting ====");
        while (flag) {
        }
        Utils.println("==== finish ====");
    }
}
