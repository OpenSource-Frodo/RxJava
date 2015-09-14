package observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.rmi.CORBA.Util;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by frodo on 2015/9/14. Observable Utility Operators
 */
public class Utility {
    public static void delayMethod() {
        PublishSubject<Integer> source = PublishSubject.create();
        final List<PublishSubject<Integer>> delays = new ArrayList<PublishSubject<Integer>>();
        final int n = 10;
        for (int i = 0; i < n; i++) {
            PublishSubject<Integer> delay = PublishSubject.create();
            delays.add(delay);
        }

        Func1<Integer, Observable<Integer>> delayFunc = new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer t1) {
                return delays.get(t1);
            }
        };

        source.delay(delayFunc);
        Client.observableSubscribe(source);

        for (int i = 0; i < n; i++) {
            source.onNext(i);
        }
        source.onCompleted();
    }

    // doOnCompleted doOnEach doOnError doOnNext doOnSubscribe doOnTerminate doOnUnsubscribe finallyDo
    public static void doOnCompletedMethod() {
        Observable<?> observable = Client.createObservable();
        observable.doOnCompleted(new Action0() {
            @Override
            public void call() {
                Utils.println("doOnCompleted called.");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void doOnEachMethod() {
        Observable<String> observable = Client.createObservable();
        observable.doOnEach(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Utils.println("doOnEach  onCompleted  called.");
            }

            @Override
            public void onError(Throwable e) {
                Utils.println("doOnEach  onError  called.");
            }

            @Override
            public void onNext(String s) {
                Utils.println("doOnEach  onNext  called.");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void doOnErrorMethod() {
        final AtomicReference<Throwable> r = new AtomicReference<Throwable>();
        Throwable t = null;
        Observable observable = null;
        try {
            observable = Observable.<String>error(new RuntimeException("an error")).doOnError(new Action1<Throwable>
                    () {

                @Override
                public void call(Throwable v) {
                    r.set(v);
                }
            });
        } catch (Throwable e) {
            t = e;
        }
        if (observable != null) {
            Client.observableSubscribe(observable);
        }
    }

    public static void doOnNextMethod() {
        Observable<String> observable = Client.createObservable();
        observable.doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.contains("one")) {
                    throw new RuntimeException("Item exceeds maximum value");
                }
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void doOnSubscribeMethod() {
        Observable<String> observable = Client.createObservable();
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                Utils.println("doOnSubscribe called.");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void doOnTerminateMethod() {
        Observable<String> observable = Client.createObservable();
        observable.doOnTerminate(new Action0() {
            @Override
            public void call() {
                Utils.println("doOnTerminate called.");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void doOnUnsubscribeMethod() {
        Observable<String> observable = Client.createObservable();
        observable.doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                Utils.println("doOnUnsubscribe called.");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void finallyDoMethod() {
        Observable<String> observable = Client.createObservable();
        observable.finallyDo(new Action0() {
            @Override
            public void call() {
                Utils.println("finallyDo called.");
            }
        });
        Client.observableSubscribe(observable);
    }

}
