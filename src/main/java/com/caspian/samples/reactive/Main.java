package com.caspian.samples.reactive;

import io.reactivex.Observable;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static Integer load(int id) {
    if (id == 1) {
      throw new IllegalStateException();
    }
    
    return id + 1;
  }

  static Observable<Integer> rxLoad(int id) {
    return Observable.create(emitter -> {
      try {
        emitter.onNext(load(id));
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    });
  }

  public static void main(String... args) {
    rxLoad(2).subscribe(System.out::println);
    rxLoad(1).subscribe(System.out::println);
  }
}
