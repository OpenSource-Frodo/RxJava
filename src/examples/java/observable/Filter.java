package observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by frodo on 2015/9/11.frodo method for Observable
 */
public class Filter {
    public static void debounceMethod() {
        Observable<?> observable = Client.createObservable().debounce(100, TimeUnit.SECONDS);
        Client.observableSubscribe(observable);
    }

    public static void distinctMethod() {
        Observable<?> observable = Client.createObservable().distinct();
        Client.observableSubscribe(observable);
    }

    public static void elementAtMethod() {
        Observable<?> observable = Client.createObservable().elementAt(2);
        Client.observableSubscribe(observable);
    }

    public static void filterMethod() {
        Observable<?> observable = Client.createObservable().filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.contains("one");
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void firstMethod() {
        Observable<?> observable = Client.createObservable().first();
        Client.observableSubscribe(observable);
    }

    public static void ignoreElementsMethod() {
        Observable<?> observable = Client.createObservable().ignoreElements();
        Client.observableSubscribe(observable);
    }

    public static void lastMethod() {
        Observable<?> observable = Client.createObservable().last();
        Client.observableSubscribe(observable);
    }

    public static void sampleMethod() {
        Observable<?> observable = Client.createObservable().sample(1, TimeUnit.SECONDS);
        Client.observableSubscribe(observable);
    }

    public static void skipMethod() {
        Observable<?> observable = Client.createObservable().skip(2);
        Client.observableSubscribe(observable);
    }

    public static void skipLastMethod() {
        Observable<?> observable = Client.createObservable().skipLast(2);
        Client.observableSubscribe(observable);
    }

    public static void takeMethod() {
        Observable<?> observable = Client.createObservable().take(2);
        Client.observableSubscribe(observable);
    }

    public static void takeLastMethod() {
        Observable<?> observable = Client.createObservable().takeLast(2);
        Client.observableSubscribe(observable);
    }
}
