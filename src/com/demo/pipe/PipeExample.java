package com.demo.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeExample {

    public static void main(String[] args) throws IOException {
        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);

        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    output.write("Hello, World!".getBytes());
                    Thread.sleep(3000);
                    output.write("I am the King of the World!".getBytes());
                    output.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data;
                    while ((data = input.read()) != -1) {
                        System.out.print((char) data);
                    }
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        writerThread.start();
        readerThread.start();
    }
}
