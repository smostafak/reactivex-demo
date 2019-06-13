package com.caspian.samples.reactive;

import io.reactivex.Observable;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  public static void main(String... args) {
    // Create reactive stream which emits only one element
    final Observable<Object> stream = Observable.create(emitter -> {
      emitter.onNext("Hello world");  // emitter.emit("Hello world");
      emitter.onComplete();                 // emitter.finish();
    });

    // Subscribe to the reactive stream and println each element as it is emitted
    stream.subscribe(System.out::println);
  }
}
