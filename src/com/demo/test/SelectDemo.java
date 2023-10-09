package com.demo.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SelectDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

        // 生產者線程1，向隊列1中放入數據
        Thread producer1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue1.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 生產者線程2，向隊列2中放入數據
        Thread producer2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue2.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消費者線程，從兩個隊列中取出數據
        Thread consumer = new Thread(() -> {
            while (true) {
                select(
                        () -> {
                            try {
                                int num = queue1.take();
                                System.out.println("From queue1: " + num);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> {
                            try {
                                int num = queue2.take();
                                System.out.println("From queue2: " + num);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        });

        producer2.start();
        producer1.start();
        consumer.start();
    }

    public static void select(Runnable... cases) {
        for (Runnable r : cases) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }

            try {
                r.run();
                break;
            } catch (Exception e) {
            }
        }
    }
}
