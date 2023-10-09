package com.demo.test;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * PipedReader与PipedWriter字符管道流
 * 北京动力节点老崔
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        //先创建字符管道流
        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        //在输入管道流与输出管道流之间建立连接
        reader.connect(writer);

        //创建一个线程向管道流中穿入数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        writer.write("data--" + i + "--" + Math.random() + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //开启另外一个 线程从管道流中读取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                char[] charArray = new char[1024];
                try {
                    int len = reader.read(charArray);
                    while (len != -1) {
                        System.out.print(String.valueOf(charArray, 0, len));
                        len = reader.read(charArray);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}