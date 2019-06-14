package com.caspian.samples.reactive;

import io.reactivex.Observable;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  public static void main(String... args) {
    Observable<String> a = Observable.create(s -> {
      new Thread(() -> {
        s.onNext("one");
        s.onNext("two");
        s.onComplete();
      }).start();
    });
    Observable<String> b = Observable.create(s -> {
      new Thread(() -> {
        s.onNext("three");
        s.onNext("four");
        s.onComplete();
      }).start();
    });
    // this subscribes to a and b concurrently,
    // and merges into a third sequential stream
    Observable.merge(a, b).subscribe(System.out::println);
  }
}
