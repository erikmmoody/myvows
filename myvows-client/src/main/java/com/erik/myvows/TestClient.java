package com.erik.myvows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    public static void main(String [] args) {
        try(final Socket client = new Socket("localhost", 1234);) {
            final OutputStream outputStream = client.getOutputStream();
            final OutputStreamWriter oswriter = 
                new OutputStreamWriter(outputStream); 
            final PrintWriter outputWriter = 
                new PrintWriter(oswriter);
            
            final InputStream inputStream = client.getInputStream();
            final BufferedReader inputReader = 
                new BufferedReader(new InputStreamReader(inputStream));
            
            final BufferedReader cmdReader = new BufferedReader(
                new InputStreamReader(System.in)
            );
            for (;;) {
                System.out.print("Command> ");
                String line = cmdReader.readLine();
                outputWriter.println(line);
                outputWriter.flush();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                line = inputReader.readLine();
                System.out.println("Server wrote: " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
