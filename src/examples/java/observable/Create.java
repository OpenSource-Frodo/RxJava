package observable;

import java.util.concurrent.TimeUnit;

import observable.Client;
import observable.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by frodo on 2015/9/11. create method for Observable
 */
public class Create {
    public static void createMethod() {
        Observable<String> observable = Client.createObservable();
        Client.observableSubscribe(observable);
    }

    public static void deferMethod() {
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Client.createObservable();
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void emptyNeverThrowMethod() {
        Observable<String> observable = Observable/*.empty()*/.never();
        Client.observableSubscribe(observable);
    }

    public static void fromMethod() {
        String[] items = new String[] {"one", "two", "three"};
        Observable<String> observable = Observable.from(items);
        Client.observableSubscribe(observable);
    }

    public static void intervalMethod() {
        Observable<Long> observable = Observable.interval(5, TimeUnit.SECONDS).take(5);
        Client.observableSubscribe(observable);
    }

    public static void justMethod() {
        Observable<String> observable = Observable.just("one");
        Client.observableSubscribe(observable);
    }

    public static void rangeMethod() {
        Observable<Integer> observable = Observable.range(0, 3);
        Client.observableSubscribe(observable);
    }

    public static void timerMethod() {
        Observable<Long> observable = Observable.timer(5, TimeUnit.SECONDS);
        Client.observableSubscribe(observable);
    }
}
