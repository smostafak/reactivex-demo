package com.caspian.samples.reactive;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

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

  public static void main(String... args) throws InterruptedException {
    Observable<BigInteger> naturalNumbers =
        Observable.create(emitter -> {
          Runnable r = () -> {
            BigInteger i = ZERO;
            while (!emitter.isDisposed()) {
              emitter.onNext(i);
              i = i.add(ONE);
            }
          };
          new Thread(r).start();
        });

    final Disposable disposable = naturalNumbers.subscribe(Main::log);

    System.out.println("After Subscribe");

    Thread.sleep(100);

    disposable.dispose();
  }
}
