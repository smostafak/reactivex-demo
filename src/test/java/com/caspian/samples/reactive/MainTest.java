package com.caspian.samples.reactive;

import com.caspian.ps.net.Connection;
import com.caspian.ps.net.TcpHandler;
import com.caspian.ps.net.client.TcpClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0629
 * @since 1.0
 */
public class MainTest {
  private static final Logger log = LoggerFactory.getLogger(MainTest.class);

  @Test
  public void testMain() throws Exception {
    AtomicInteger i = new AtomicInteger(0);
    try {
      TcpClientManager
          .newInstance()
          .setHost("127.0.0.1")
          .setPort(8033)
          .setHandler(new TcpHandler() {
            @Override
            public void onOpen(Connection connection) throws Exception {
              log.info(">>> CLIENT: Open");
            }

            @Override
            public void onRead(byte[] message, Connection connection) throws Exception {
              log.info(">>> CLIENT: READ '{}'" , new String(message));
              if (i.intValue() < 100) connection.write(("Ya Ali " + i.incrementAndGet()).getBytes());
            }

            @Override
            public void onWrite(Connection connection) throws Exception {
              log.info(">>> CLIENT: Write");
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
  }
}