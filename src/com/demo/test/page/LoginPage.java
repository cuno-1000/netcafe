package com.demo.test.page;

import javax.swing.*;
import java.awt.*;

/**
 * @author kt
 */
public class LoginPage extends JFrame {
    //   构造函数
    public LoginPage() {
//       初始化
        init();
    }

    //    测试函数
    public static void main(String[] args) {
        LoginPage login_page = new LoginPage();
    }

    public void init() {
        JFrame frame = new JFrame("网咖管理系统");
        frame.setLayout(null);

//        中间容器
//        JPanel panel1 = new JPanel();
        JPanel panel1 = new JPanel(null, true);

//
        ImageIcon image1 = new ImageIcon("src/com/netcafe/images/user/登录.png");
        JLabel system_image1 = new JLabel(image1);
        system_image1.setBounds(125, 15, image1.getIconWidth(), image1.getIconHeight());
        panel1.add(system_image1);

//        账号图标
        ImageIcon image2 = new ImageIcon("src/com/netcafe/images/user/账号.png");

//        账号标签
        JLabel id_JLabel = new JLabel("账号");
        id_JLabel.setIcon(image2);
        id_JLabel.setFont(new Font("宋体标题", Font.BOLD, 25));
        id_JLabel.setBounds(20, 250, 90, 35);
        id_JLabel.setForeground(Color.BLACK);
        panel1.add(id_JLabel);

//        账号输入框
        JTextField id_JTextField = new JTextField(20);
        id_JTextField.setFont(new Font("宋体", Font.BOLD, 20));
        id_JTextField.setBounds(120, 250, 250, 35);
        id_JTextField.setForeground(Color.BLACK);
        id_JTextField.setEditable(true);
        panel1.add(id_JTextField);
//
//        密码图标
        ImageIcon image3 = new ImageIcon("src/com/netcafe/images/user/密码.png");

//        密码标签
        JLabel password_JLabel = new JLabel("密码");
        password_JLabel.setIcon(image3);
        password_JLabel.setFont(new Font("宋体", Font.BOLD, 25));
        password_JLabel.setBounds(20, 310, 90, 35);
        password_JLabel.setForeground(Color.BLACK);
        panel1.add(password_JLabel);
//
//        密码输入框
        JPasswordField password_JPasswordField = new JPasswordField(20);
        password_JPasswordField.setFont(new Font("宋体", Font.BOLD, 20));
        password_JPasswordField.setEchoChar('*');
        password_JPasswordField.setBounds(120, 310, 250, 35);
        password_JPasswordField.setEditable(true);
        panel1.add(password_JPasswordField);
//
        ImageIcon image4 = new ImageIcon("src/com/netcafe/images/user/登录按钮.png");

//        登录按钮
        JButton login_button = new JButton("登录", image4);
        login_button.setFont(new Font("宋体标题", Font.BOLD, 25));
        login_button.setForeground(Color.BLACK);
        login_button.setBounds(150, 370, 150, 35);
        login_button.setContentAreaFilled(false);
        panel1.add(login_button);

//
        ImageIcon image5 = new ImageIcon("src/com/netcafe/images/user/注册.png");

//        注册按钮
        JButton register_button = new JButton("未有账号，去注册", image5);
        register_button.setFont(new Font("宋体标题", Font.BOLD, 20));
        register_button.setForeground(Color.BLACK);
        register_button.setBounds(100, 410, 250, 35);
//        按钮设置为透明
        register_button.setContentAreaFilled(false);
        panel1.add(register_button);

//        中间容器
        panel1.setBounds(0, 0, 600, 700);
//        panel1.setBackground(Color.lightGray);
        panel1.setVisible(true);
        frame.add(panel1);

//
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

