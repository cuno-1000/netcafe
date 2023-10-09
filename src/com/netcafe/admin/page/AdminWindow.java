package com.netcafe.admin.page;

import com.netcafe.admin.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AdminWindow extends JFrame {

    public Server server = null;
    CardLayout cardLayout = new CardLayout();

    public static JOptionPane optionPane = new JOptionPane();

    /**
     * Create the frame.
     */
    public AdminWindow() {
        setTitle("主界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 610, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setLocationRelativeTo(null);//让窗口在屏幕中间显示

        this.setResizable(false);//让窗口大小不可改变

        contentPane.setLayout(null);

        JPanel panel = new JPanel(); // 刘德文 // 管理员端:导航栏式的单窗口多页面跳转
        panel.setBounds(5, 37, 600, 390);
        contentPane.add(panel);
//		给主要显示面板添加布局方式
        panel.setLayout(cardLayout);
//		创建相应面板类的对象
        UserInfoPage p1 = new UserInfoPage();
//		将面板添加到住面板中，注意:add()方法里有两个参数，第一个是要添加的对象，第二个给这个对象所放置的卡片
//		起个名字，后面调用显示的时候要用到这个名字
        panel.add(p1, "UserList");

        JButton userListBtn = new JButton("UserList");
        userListBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//				这里是点击按钮切换不同的页面
//				cardLayout.show(参数 1，参数2)方法里面也有两个参数，
//				参数1是表示指明你要在哪个容器上显示，
//				参数2是指明要显示哪个卡片，即你要给出对应卡片的名字
                UserInfoPage p1 = new UserInfoPage();
                panel.add(p1, "UserList");
                cardLayout.show(panel, "UserList");
            }
        });
        userListBtn.setBounds(10, 13, 147, 24);
        contentPane.add(userListBtn);

        JButton incomeBtn = new JButton("IncomeDetail");
        incomeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//				同上
                IncomePage p2 = new IncomePage();
                panel.add(p2, "IncomeDetail");
                cardLayout.show(panel, "IncomeDetail");
            }
        });
        incomeBtn.setBounds(158, 13, 147, 24);
        contentPane.add(incomeBtn);

        JButton consumeLogBtn = new JButton("ConsumeLog");
        consumeLogBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConsumePage p3 = new ConsumePage();
                panel.add(p3, "ConsumePage");
                cardLayout.show(panel, "ConsumePage");
            }
        });
        consumeLogBtn.setBounds(305, 13, 147, 24);
        contentPane.add(consumeLogBtn);

        JButton onlineUserBtn = new JButton("OnlinePage");
        onlineUserBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OnlinePage p4 = new OnlinePage(AdminWindow.this);
                panel.add(p4, "OnlinePage");
                cardLayout.show(panel, "OnlinePage");
            }
        });
        onlineUserBtn.setBounds(452, 13, 147, 24);
        contentPane.add(onlineUserBtn);

        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                server.stop();
                System.exit(0);
            }
        });
    }

    public static void showCalling(String s) {
        optionPane.showMessageDialog(null, s);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            final int port = 65431;

            public void run() {
                try {
                    AdminWindow frame = new AdminWindow();
                    frame.server = new Server(port);
                    frame.server.frame = frame;
                    frame.server.start();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}