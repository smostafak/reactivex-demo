package com.caspian.samples.reactive;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static void log(Object msg) {
    System.out.println(
        Thread.currentThread().getName() +
        ": " + msg);
  }

  public static void main(String... args) {
    Observable<Integer> ints = Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        log("Create");
        emitter.onNext(5);
        emitter.onNext(6);
        emitter.onNext(7);
        emitter.onComplete();
        log("Completed");
      }
    });

    log("Starting");
    ints.subscribe(i -> log("Element: " + i));
    log("Exit");
  }
}
