package com.netcafe.user.page;

import com.netcafe.common.param.Password;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PasswordPage extends JPanel {
    UserWindow frame;

    public PasswordPage(UserWindow a)
    {
        frame=a;
        setLayout(null);
        setDoubleBuffered(true);

        ImageIcon image1=new ImageIcon(frame.imagePath+ "修改密码主界面.png");
        JLabel system_image1 = new JLabel(image1);
        system_image1.setBounds(170, 10, image1.getIconWidth(), image1.getIconHeight());
        add(system_image1);

        ImageIcon image2=new ImageIcon(frame.imagePath+ "密码.png");

        JLabel oldPasswordLabel = new JLabel("原密码:");
        oldPasswordLabel.setIcon(image2);
        oldPasswordLabel.setBounds(20, 170, 150, 40);
        oldPasswordLabel.setFont(new Font("宋体标题", Font.BOLD, 25));
        oldPasswordLabel.setForeground(Color.BLACK);
        add(oldPasswordLabel);

        JPasswordField oldPasswordField = new JPasswordField(20);
        oldPasswordField.setBounds(180, 170, 250, 40);
        oldPasswordField.setFont(new Font("宋体", Font.BOLD, 25));
        oldPasswordField.setForeground(Color.BLACK);
        oldPasswordField.setEditable(true);
        add(oldPasswordField);

        ImageIcon image3=new ImageIcon(frame.imagePath+ "密码.png");
        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordLabel.setIcon(image3);
        newPasswordLabel.setBounds(20, 230, 150, 40);
        newPasswordLabel.setFont(new Font("宋体标题", Font.BOLD, 25));
        newPasswordLabel.setForeground(Color.BLACK);
        add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setBounds(180, 230, 250, 40);
        newPasswordField.setFont(new Font("宋体", Font.BOLD, 25));
        newPasswordField.setForeground(Color.BLACK);
        newPasswordField.setEditable(true);
        add(newPasswordField);

        ImageIcon image4=new ImageIcon(frame.imagePath+ "确认.png");
        JLabel confirmPasswordLabel = new JLabel("确认:");
        confirmPasswordLabel.setIcon(image4);
        confirmPasswordLabel.setBounds(20, 290, 150, 40);
        confirmPasswordLabel.setFont(new Font("宋体标题", Font.BOLD, 25));
        confirmPasswordLabel.setForeground(Color.BLACK);
        add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(180, 290, 250, 40);
        confirmPasswordField.setFont(new Font("宋体", Font.BOLD, 25));
        confirmPasswordField.setForeground(Color.BLACK);
        confirmPasswordField.setEditable(true);
        add(confirmPasswordField);

        ImageIcon image6=new ImageIcon(frame.imagePath+ "确认.png");
        JButton confirmButton = new JButton("确认",image6);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!newPasswordField.getText().equals(confirmPasswordField.getText())){
                    JOptionPane.showMessageDialog(null, "确认密码与新设置密码输入不一致");
                    return;
                }
                if(newPasswordField.getText().equals(oldPasswordField.getText())) {
                    JOptionPane.showMessageDialog(null, "新密码与原密码一致");
                    return;
                }
                Password a = new Password(
                        frame.member.getMemberID(), frame.member.getPhone(),
                        oldPasswordField.getText(),newPasswordField.getText());
                try {
                    frame.client.sendObject(a);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                oldPasswordField.setText("");
                newPasswordField.setText("");
                confirmPasswordField.setText("");
            }
        });
        confirmButton.setFont(new Font("宋体标题", Font.BOLD, 25));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setBounds(80, 370, 150, 40);
        confirmButton.setContentAreaFilled(false);
        add(confirmButton);

        ImageIcon image8=new ImageIcon(frame.imagePath+ "返回.png");
        JButton btnNewButton1 = new JButton("返回",image8);
        btnNewButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.cardLayout.show(frame.panel, "p3");
            }
        });
        btnNewButton1.setBounds(260, 370, 150, 40);
        btnNewButton1.setFont(new Font("宋体标题", Font.BOLD, 25));
        btnNewButton1.setForeground(Color.BLUE);
        btnNewButton1.setContentAreaFilled(false);
        add(btnNewButton1);
    }
}
