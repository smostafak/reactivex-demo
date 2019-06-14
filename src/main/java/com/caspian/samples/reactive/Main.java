package com.caspian.samples.reactive;

import io.reactivex.Observable;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

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
    // BROKEN! Don't do this
    Observable<BigInteger> naturalNumbers =
        Observable.create(
            emitter -> {
              BigInteger i = ZERO;
              while (true) { // don't do this!
                emitter.onNext(i);
                i = i.add(ONE);
              }
            });
    naturalNumbers.subscribe(Main::log);
    System.out.println("After Subscribe");
  }
}
