package com.demo.pipe;

class SharedObject {
    private boolean flag = false;

    synchronized void waitForFlag() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void setFlag() {
        flag = true;
        notify();
    }

    public static void main(String[] args) {
        SharedObject sharedObject = new SharedObject();
        WaitThread waitThread = new WaitThread(sharedObject);
        NotifyThread notifyThread = new NotifyThread(sharedObject);
        waitThread.start();
        notifyThread.start();
    }
}

class WaitThread extends Thread {
    private SharedObject sharedObject;

    WaitThread(SharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    public void run() {
        System.out.println("WaitThread is waiting for flag to be set...");
        sharedObject.waitForFlag();
        System.out.println("WaitThread has been notified.");
    }
}

class NotifyThread extends Thread {
    private SharedObject sharedObject;

    NotifyThread(SharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    public void run() {
        System.out.println("NotifyThread is sleeping for 5 seconds...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sharedObject.setFlag();
        System.out.println("NotifyThread has set flag to true.");
    }
}
