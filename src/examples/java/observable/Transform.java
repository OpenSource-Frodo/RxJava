package observable;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * Created by frodo on 2015/9/11.create method for Observable
 */
public class Transform {
    public static void bufferMethod() {
        Observable<?> observable = Client.createObservable().buffer(3, 2);
        Client.observableSubscribe(observable);
    }

    public static void flatMapMethod() {
        Observable<?> observable = Client.createObservable().flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just("[flatMap call >>] " + s);
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void groupByMethod() {
        String[] items = new String[] {"a-one", "b-one", "a-two", "b-two", "a-three", "b-three"};
        Observable observable = Observable
                .from(items)
                .groupBy(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        final String str = String.valueOf(s.charAt(0));
                        return str;
                    }
                })
                .flatMap(new Func1<GroupedObservable<String, String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(GroupedObservable<String, String> stringStringGroupedObservable) {
                        return stringStringGroupedObservable.map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                final String str = String.valueOf(s.charAt(0));
                                return str;
                            }
                        });
                    }
                });
        Client.observableSubscribe(observable);
    }

    public static void mapMethod() {
        Observable<?> observable = Client.createObservable().map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                if (s.equals("two")) {
                    return "something";
                }
                return s;
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void scanMethod() {
        Observable<?> observable = Client.createObservable().scan(new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                //                Utils.println("Scan >> call: " + s + ", " + s2);
                return String.format("tag [%s, %s]", s, s2);
            }
        });
        Client.observableSubscribe(observable);
    }

    public static void windowMethod() {
        Client.createObservable()
                .window(2)
                .map(new Func1<Observable<String>,
                        Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(Observable<String> xs) {
                        return xs.toList();
                    }
                })
                .toBlocking()
                .forEach(new Action1<Observable<List<String>>>() {
                    @Override
                    public void call(Observable<List<String>> listObservable) {
                        listObservable.subscribe(new Action1<List<String>>() {
                            @Override
                            public void call(List<String> strings) {
                                for (String str : strings) {
                                    Utils.println(str);
                                }
                            }
                        });
                    }
                });
    }
}
