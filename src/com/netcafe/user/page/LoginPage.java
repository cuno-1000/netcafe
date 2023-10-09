package com.netcafe.user.page;

import com.netcafe.common.param.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPage extends JPanel {
    UserWindow frame;

    /**
     * Create the panel.
     */
    public LoginPage(UserWindow a) {
        frame = a;

        setLayout(null);
        setDoubleBuffered(true);

        ImageIcon image1 = new ImageIcon(frame.imagePath+ "登录界面.png"); // 刘德文
        JLabel system_image1 = new JLabel(image1);
        system_image1.setBounds(125, 15, image1.getIconWidth(), image1.getIconHeight());
        add(system_image1);

//        账号图标
        ImageIcon image2 = new ImageIcon(frame.imagePath+ "账号.png");

//        账号标签
        JLabel id_JLabel = new JLabel("账号");
        id_JLabel.setIcon(image2);
        id_JLabel.setFont(new Font("宋体标题", Font.BOLD, 25));
        id_JLabel.setBounds(20, 250, 90, 35);
        id_JLabel.setForeground(Color.BLACK);
        add(id_JLabel);

//        账号输入框
        JTextField id_JTextField = new JTextField(20);
        id_JTextField.setFont(new Font("宋体", Font.BOLD, 20));
        id_JTextField.setBounds(120, 250, 250, 35);
        id_JTextField.setForeground(Color.BLACK);
        id_JTextField.setEditable(true);
        add(id_JTextField);
//
//        密码图标
        ImageIcon image3 = new ImageIcon(frame.imagePath+ "密码.png");

//        密码标签
        JLabel password_JLabel = new JLabel("密码");
        password_JLabel.setIcon(image3);
        password_JLabel.setFont(new Font("宋体", Font.BOLD, 25));
        password_JLabel.setBounds(20, 310, 90, 35);
        password_JLabel.setForeground(Color.BLACK);
        add(password_JLabel);
//
//        密码输入框
        JPasswordField password_JPasswordField = new JPasswordField(20);
        password_JPasswordField.setFont(new Font("宋体", Font.BOLD, 20));
        password_JPasswordField.setEchoChar('*');
        password_JPasswordField.setBounds(120, 310, 250, 35);
        password_JPasswordField.setEditable(true);
        add(password_JPasswordField);
//
        ImageIcon image4 = new ImageIcon(frame.imagePath+ "登录按钮.png");

//        登录按钮
        JButton login_button = new JButton("登录", image4);
        login_button.setFont(new Font("宋体标题", Font.BOLD, 25));
        login_button.setForeground(Color.BLACK);
        login_button.setBounds(150, 370, 150, 35);
        login_button.setContentAreaFilled(false);
        add(login_button);

        login_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login a = new Login();
                a.setPhone(id_JTextField.getText());
                a.setPassword(password_JPasswordField.getText());
//                a.setPhone("15322033900");
//                a.setPassword("12345678");
                try {
                    frame.client.sendObject(a);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

/*
// 用户注册功能已弃用 2023.05.17
        ImageIcon image5 = new ImageIcon("src/com/netcafe/images/user/注册.png");

//        注册按钮
        JButton register_button = new JButton("未有账号，去注册", image5);
        register_button.setFont(new Font("宋体标题", Font.BOLD, 20));
        register_button.setForeground(Color.BLACK);
        register_button.setBounds(100, 410, 250, 35);
//        按钮设置为透明
        register_button.setContentAreaFilled(false);
        add(register_button);

        register_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.cardLayout.show(frame.panel, "p2");
            }
        });
*/

    }
}
