package com.caspian.samples.reactive;

import io.reactivex.Observable;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static void log(Object msg) {
    System.out.println(System.currentTimeMillis() + " "+ Thread.currentThread().getName() + ": " + msg);
  }

  public static void main(String... args) throws InterruptedException {
    Observable
        .interval(1_000_000 / 60, MICROSECONDS)
        .subscribe(Main::log);

    Thread.currentThread().join(2000);
  }
}
