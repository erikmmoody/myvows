package com.erik.myvows.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 */
public final class TestServer  {

    public static void main (String [] args) {
        try (final ServerSocket server = new ServerSocket(1234);) {
            for (;;) {
                final Socket socket = server.accept();
                new ServerInstance(socket).start();
                System.out.println("Chillin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
