package com.erik.myvows.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerInstance extends Thread {

    private final Socket socket;
    private boolean terminate;

    public ServerInstance(Socket socket) {
        this.socket = socket;
        terminate = false;
    }

    private void process(BufferedReader input,
                        PrintWriter output) throws IOException {
        final String line = input.readLine();
        // Echo back the line
        System.out.println("Got a message: " + line);
        output.println("Echo from server: " + line);
        output.flush();
        System.out.println("We wrote back: " + line);
        if (line.equalsIgnoreCase("exit")) {
            this.terminate = true;
            System.out.println("Connection terminated");
        }
        
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = socket.getInputStream();
            final BufferedReader inputReader =
                new BufferedReader(
                    new InputStreamReader(inputStream)
            );
            final OutputStream outputStream = 
                socket.getOutputStream();
            final PrintWriter outputWriter = 
                new PrintWriter(
                    new OutputStreamWriter(
                        outputStream
                    )
                );
            while (!terminate) {
                process(inputReader, outputWriter);
            }

            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
