package observable;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by frodo on 2015/9/11.Combine method for Observable
 */
public class Combine {
    public static void joinMethod() {
        Observable<?> observable = Client.createObservable()
                .join(
                        Observable.just("join"),
                        new Func1<String, Observable<String>>() {
                            @Override
                            public Observable<String> call(String s) {
                                return Observable.just(s);
                            }
                        },
                        new Func1<String, Observable<String>>() {
                            @Override
                            public Observable<String> call(String s) {
                                return Observable.just(s);
                            }
                        },
                        new Func2<String, String, String>() {
                            @Override
                            public String call(String s, String s2) {
                                Utils.println(s + s2);
                                return s + "," + s2;
                            }
                        }
                );
        Client.observableSubscribe(observable);
    }

    public static void mergeMethod() {
        Observable<?> observable1 = Client.createObservable();
        Observable<?> observable2 = Observable.just("merge");
        Observable observable = Observable.merge(observable2, observable1);
        Client.observableSubscribe(observable);
    }

    public static void startWithMethod() {
        Observable<?> observable = Client.createObservable().startWith("startWith");
        Client.observableSubscribe(observable);
    }

    public static void switchIfEmptyMethod() {
        Observable<?> observable = Client.createObservable().flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return Observable.just(s);
            }
        }).switchIfEmpty(Observable.just("switchIfEmpty"));
        Client.observableSubscribe(observable);
    }

    public static void zipWithMethod() {
        Iterable<String> it = Arrays.asList("a", "b", "c");
        Observable<?> observable = Client.createObservable().zipWith(it, new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + ", " + s2;
            }
        });
        Client.observableSubscribe(observable);
    }
}
