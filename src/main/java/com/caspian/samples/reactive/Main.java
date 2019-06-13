package com.caspian.samples.reactive;

import io.reactivex.Observable;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  public static void main(String... args) {
    Observable
        .create(emitter -> new Thread(() -> {
          emitter.onNext(1);
          emitter.onNext(2);
          emitter.onNext(3);
          emitter.onNext(4);
          emitter.onNext(5);
          emitter.onComplete();
        }).start())
        .doOnNext(i -> System.out.println(Thread.currentThread().getName()))
        .map(i -> "Value " + i + " processed on " + Thread.currentThread().getName())
        .subscribe(System.out::println);
    System.out.println("Will be run on thread '" +  Thread.currentThread().getName() + "' and print BEFORE values are emitted");
  }
}
