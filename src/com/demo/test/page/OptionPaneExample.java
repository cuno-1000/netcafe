package com.demo.test.page;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

public class OptionPaneExample {
    public static void main(String[] args) throws InterruptedException {
//        JOptionPane optionPane = new JOptionPane();
//        optionPane.showMessageDialog(null, "歡迎使用JOptionPane歡迎使用JOptionPane歡迎使用JOptionPane歡迎使用JOptionPane！");
//        optionPane.showMessageDialog(null, "您確定要退出嗎？");
        JOptionPane pane = new JOptionPane("這是一個對話框");
        JDialog dialog = pane.createDialog("標題");
        dialog.setVisible(true); // 顯示對話框
//
//// 關閉對話框的方法一
////        dialog.setVisible(false);
//
//// 關閉對話框的方法二
//        dialog.dispose();


//        JDialog dialog = (new JOptionPane("请五分钟内到前台充值")).createDialog("余额不足");
//
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            int a = 5;
//            @Override
//            public void run() {
//                if(a==0){
//                    dialog.dispose();
//                    timer.cancel();
//                }
//                // 延遲顯示 JOptionPane 的任務
//
//
//                // 周期性執行的任務
//                // ...
//                System.out.println(a);
//                a--;
//            }
//        };
//
//
//        timer.scheduleAtFixedRate(task, 0, 1000); // 每秒執行一次

//        JDialog dialog = new JDialog();
//
//// 創建自定義面板
//        JPanel panel = new JPanel();
//        panel.add(new JLabel("這是一個對話框"));
//        dialog.add(panel);
//
//        dialog.pack();
//
//        dialog.setLocation(100, 100);
//        dialog.setVisible(true); // 顯示對話框

// 修改面板中的元件
//        Component[] components = panel.getComponents();
//        for (Component component : components) {
//            if (component instanceof JLabel) {
//                JLabel label = (JLabel) component;
//                label.setText("新的內容"); // 修改標籤文字
//            }
//        }
        Thread.sleep(5000);
        dialog.dispose();
    }
}
