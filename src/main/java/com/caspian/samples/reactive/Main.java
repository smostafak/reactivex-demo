package com.caspian.samples.reactive;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static void log(Object msg) {
    System.out.printf("[%s]: %s\n", Thread.currentThread().getName(), msg);
  }

  private static void sleep(int timeout, TimeUnit unit) {
    try {
      unit.sleep(timeout);
    } catch (InterruptedException ignored) {
      //intentionally ignored
    }
  }

  private static <T> Observable<T> delayed(T x) {
    return Observable.create(emitter -> {
          Runnable r = () -> {
            sleep(10, SECONDS);
            if (!emitter.isDisposed()) {
              emitter.onNext(x);
              emitter.onComplete();
            }
          };
          new Thread(r).start();
        });
  }

  public static void main(String... args) throws InterruptedException {

    final Disposable disposable = delayed(10).subscribe(Main::log);

    System.out.println("After Subscribe");

    sleep(1, SECONDS);

    disposable.dispose();
  }
}
