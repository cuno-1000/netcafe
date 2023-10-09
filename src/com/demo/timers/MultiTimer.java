package com.demo.timers;

import java.util.Timer;
import java.util.TimerTask;

public class MultiTimer {
    public int amount;
    public static void main(String[] args) {
        MultiTimer m = new MultiTimer();
        m.amount = 10;

        Timer timer = new Timer();

        long startTime;

        startTime = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 查看是否夠錢，不夠->提醒
                System.out.println("balance");
                if(m.amount <2) System.out.println("不夠");
                else System.out.println("夠");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // 五分鐘後查看，夠錢->扣錢/不夠->強制下線
//                        System.out.println("deduct/logout");
//                        // 強制下線前，取消定時器線程再退出
//                        timer.cancel();

                        if(m.amount <2){
                            System.out.println("不夠,下線");
                            m.amount -=2;
                        } else {
                            System.out.println("夠,扣減");
                        }
                    }
                }, 1000);
            }
        }, 0, 3000);
    }
}
