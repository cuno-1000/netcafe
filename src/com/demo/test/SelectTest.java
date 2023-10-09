package com.demo.test;

public class SelectTest {

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
