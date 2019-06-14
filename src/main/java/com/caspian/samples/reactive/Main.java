package com.caspian.samples.reactive;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

import java.util.concurrent.TimeUnit;

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
      System.out.println("InterruptedException");
    }
  }

  static <T> Observable<T> delayed(T x) {
    return Observable.create(
        subscriber -> {
          Runnable r = () -> {
            sleep(10, SECONDS);
            if (!subscriber.isDisposed()) {
              subscriber.onNext(x);
              subscriber.onComplete();
            }
          };
          final Thread thread = new Thread(r);
          thread.start();
          subscriber.setDisposable(Disposables.fromAction(thread::interrupt));
        });
  }

  public static void main(String... args) {
    final Disposable disposable = delayed(10).subscribe(Main::log);

    System.out.println("After Subscribe");

    sleep(1, SECONDS);

    disposable.dispose();
  }
}
