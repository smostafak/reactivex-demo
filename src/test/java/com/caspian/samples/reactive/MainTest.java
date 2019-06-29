package com.caspian.samples.reactive;

import com.caspian.ps.net.Connection;
import com.caspian.ps.net.TcpHandler;
import com.caspian.ps.net.client.TcpClientManager;
import org.testng.annotations.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0629
 * @since 1.0
 */
public class MainTest {

  @Test
  public void testMain() throws Exception {
    Executors.newSingleThreadExecutor().submit(() -> {
      AtomicInteger i = new AtomicInteger(0);
      try {
        TcpClientManager
            .newInstance()
            .setHost("127.0.0.1")
            .setPort(8033)
            .setHandler(new TcpHandler() {
              @Override
              public void onOpen(Connection connection) throws Exception {
                System.out.println(">>> CLIENT: Open");
//                connection.write("Ya Ali".getBytes());
              }

              @Override
              public void onRead(byte[] message, Connection connection) throws Exception {
                System.out.println(">>> CLIENT: READ '" + new String(message) + "'");
                if (i.intValue() < 100) connection.write(("Ya Ali " + i.incrementAndGet()).getBytes());
              }

              @Override
              public void onWrite(Connection connection) throws Exception {
                System.out.println(">>> CLIENT: Write");
                if (i.intValue() < 100) connection.write(("Ya Ali " + i.incrementAndGet()).getBytes());
              }

              @Override
              public void onFail(Exception e) throws Exception {
                e.printStackTrace();
              }
            })
            .start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });


    Thread.currentThread().join();

  }
}