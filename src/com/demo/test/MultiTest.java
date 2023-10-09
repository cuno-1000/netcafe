package com.demo.test;

public class MultiTest {
    public static void main(String[] args) {
        Thread p1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 1 is running");
        });
        Thread p2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task 2 is running");
        });

        p1.start();
        p2.start();

        System.out.println("running");
    }
}
