package com.caspian.samples.reactive;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static void log(Object msg) {
    System.out.println(Thread.currentThread().getName() + ": " + msg);
  }

  public static void main(String... args) throws InterruptedException {
    Observable
        .timer(1, TimeUnit.SECONDS)       // an asynchronous equivalent of Thread.sleep().
        .subscribe(Main::log);

    Thread.currentThread().join(2000);
  }
}
