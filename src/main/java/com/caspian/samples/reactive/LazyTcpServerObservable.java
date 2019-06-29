package com.caspian.samples.reactive;

import com.caspian.ps.net.Connection;
import com.caspian.ps.net.TcpHandler;
import com.caspian.ps.net.server.TcpServer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0629
 * @since 1.0
 */
public final class LazyTcpServerObservable {
  private static final Logger log = LoggerFactory.getLogger(LazyTcpServerObservable.class);

  private TcpServer server;
  private Observable<String> observable;
  private final Set<ObservableEmitter<? super String>> subscribers = new CopyOnWriteArraySet<>();

  private synchronized void register(ObservableEmitter<? super String> subscriber) {
    subscribers.add(subscriber);
  }

  private synchronized void deregister(ObservableEmitter<? super String> subscriber) throws IOException {
    subscribers.remove(subscriber);
    if (subscribers.isEmpty()) {
      server.stop();
    }
  }

  private void startTcpServer() throws Exception {
    server = new TcpServer(8033, new TcpHandler() {
      @Override
      public void onOpen(Connection connection) throws Exception {
        log.info(">>> SERVER: Open");
      }

      @Override
      public void onRead(byte[] message, Connection connection) throws Exception {
        final String text = new String(message);
//      log.info(">>> SERVER: Read '{}'", text);
        connection.write(String.valueOf(message.length).getBytes());

        subscribers.forEach(s -> s.onNext(text));
      }

      @Override
      public void onFail(Exception e) throws Exception {
        e.printStackTrace();
        subscribers.forEach(s -> s.onError(e));
      }
    });
    server.start();
  }

  Observable<String> observe() { return observable; }

  private void run() throws Exception {
    observable =
        Observable.create(
            emitter -> {
              register(emitter);
              emitter.setDisposable(Disposables.fromAction(() -> this.deregister(emitter)));
            });
    observable.subscribe(log::info);
    observable.subscribe(log::warn);
    startTcpServer();
  }

  public static void main(String... args) throws Exception {
    new LazyTcpServerObservable().run();
  }
}
