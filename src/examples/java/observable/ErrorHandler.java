package observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by frodo on 2015/9/14. Error Handling Operators
 */
public class ErrorHandler {
    private static class TestObservable implements Observable.OnSubscribe<String> {

        final String[] values;
        Thread t = null;

        public TestObservable(String... values) {
            this.values = values;
        }

        @Override
        public void call(final Subscriber<? super String> subscriber) {
            System.out.println("TestObservable subscribed to ...");
            t = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println("running TestObservable thread");
                        for (String s : values) {
                            System.out.println("TestObservable onNext: " + s);
                            subscriber.onNext(s);
                        }
                        throw new RuntimeException("Forced Failure");
                    } catch (Throwable e) {
                        subscriber.onError(e);
                    }
                }

            });
            System.out.println("starting TestObservable thread");
            t.start();
            System.out.println("done starting TestObservable thread");
        }
    }

    public static void onErrorReturnMethod() {
        TestObservable f = new TestObservable("one");
        Observable<String> w = Observable.create(f);

        final AtomicReference<Throwable> capturedException = new AtomicReference<Throwable>();

        Observable<String> observable = w.onErrorReturn(new Func1<Throwable, String>() {
            @Override
            public String call(Throwable e) {
                capturedException.set(e);
                throw new RuntimeException("exception from function");
            }
        });

        Client.observableSubscribe(observable);
    }

    public static void onErrorResumeNextMethod() {
        final AtomicReference<Throwable> receivedException = new AtomicReference<Throwable>();
        Observable<String> w = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> observer) {
                observer.onNext("one");
                observer.onError(new Throwable("injected failure"));
                observer.onNext("two");
                observer.onNext("three");
            }
        });

        Func1<Throwable, Observable<String>> resume = new Func1<Throwable, Observable<String>>() {

            @Override
            public Observable<String> call(Throwable t1) {
                receivedException.set(t1);
                return Observable.just("twoResume", "threeResume");
            }

        };
        Observable<String> observable = w.onErrorResumeNext(resume);
        Client.observableSubscribe(observable);
    }

    public static void onExceptionResumeNextMethod() {
        TestObservable f = new TestObservable("one", "EXCEPTION", "two", "three");
        Observable<String> w = Observable.create(f);
        Observable<String> resume = Observable.just("twoResume", "threeResume");
        Observable<String> observable = w.onExceptionResumeNext(resume);
        Client.observableSubscribe(observable);
    }

    public static void retryMethod() {
        final AtomicInteger count = new AtomicInteger();

        Observable<String> origin = Client.createObservable()
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "msg: " + count.incrementAndGet() + " " + s;
                    }
                });
        Client.observableSubscribe(origin);
        Utils.println(">>>> divider <<<<");
        //origin.retry();
        origin.retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer integer, Throwable throwable) {
                return integer==2;
            }
        });
        Client.observableSubscribe(origin);
    }
}
