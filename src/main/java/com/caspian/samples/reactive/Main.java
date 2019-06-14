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
      s.onNext("one");
      s.onNext("two");
      s.onNext("three");
      s.onNext("four");
      s.onComplete();
    });

    a.subscribe(System.out::println);
    a.subscribe(System.out::println);
  }
}
