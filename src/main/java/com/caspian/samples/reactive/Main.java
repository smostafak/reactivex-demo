package com.caspian.samples.reactive;


import com.caspian.ps.net.server.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.caspian.ps.net.Connection;
import com.caspian.ps.net.TcpHandler;

/**
 * @author Mostafa Kalantar (kalantar@caspco.ir)
 * @version 1.0 2019.0613
 * @since 1.0
 */
public final class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String... args) throws Exception {
    new TcpServer(8033, new TcpHandler() {
         @Override
         public void onOpen(Connection connection) throws Exception {
           System.out.println(">>> SERVER: Open");
         }

         @Override
         public void onRead(byte[] message, Connection connection) throws Exception {
           System.out.println(">>> SERVER: Read '" + new String(message) + "'");
           connection.write(String.valueOf(message.length).getBytes());
         }

         @Override
         public void onWrite(Connection connection) throws Exception {
         }

         @Override
         public void onFail(Exception e) throws Exception {
           e.printStackTrace();
         }
       })
        .start();

    Thread.currentThread().join();
  }
}
