package com.caspian.samples.reactive;

import io.reactivex.Observable;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static void log(Object msg) {
    System.out.printf("[%s]: %s\n", Thread.currentThread().getName(), msg);
  }

  public static void main(String... args) {
    Observable<Integer> ints =
        Observable.create(emitter -> {
              log("Create");
              emitter.onNext(42);
              emitter.onComplete();
            }
        );
    log("Starting");
    ints.subscribe(i -> log("Element A: " + i));
    ints.subscribe(i -> log("Element B: " + i));
    log("Exit");
  }
}
