package com.caspian.samples.reactive;

import com.caspian.ps.net.Connection;
import com.caspian.ps.net.TcpHandler;
import com.caspian.ps.net.server.TcpServer;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0629
 * @since 1.0
 */
public final class TcpServerSubject {
  private static final Logger log = LoggerFactory.getLogger(TcpServerSubject.class);

  private TcpServer server;
  private final PublishSubject<String> subject = PublishSubject.create();

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

        subject.onNext(text);
      }

      @Override
      public void onFail(Exception e) throws Exception {
        e.printStackTrace();
        subject.onError(e);
      }
    });
    server.start();
  }

  Observable<String> observe() { return subject; }

  private void run() throws Exception {
    observe().subscribe(log::info);
    observe().subscribe(log::warn);
    startTcpServer();
  }

  public static void main(String... args) throws Exception {
    new TcpServerSubject().run();
  }
}
