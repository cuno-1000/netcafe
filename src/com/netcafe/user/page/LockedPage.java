package com.netcafe.user.page;

import com.netcafe.common.param.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class LockedPage extends JPanel {
    UserWindow frame;

    /**
     * Create the panel.
     */
    public LockedPage(UserWindow a) {
        frame = a;

        setLayout(null);

        ImageIcon image1 = new ImageIcon(frame.imagePath+ "锁定.png");
        JLabel lblNewLabel = new JLabel("已锁定");
        lblNewLabel.setIcon(image1);
        lblNewLabel.setFont(new Font("宋体标题", Font.BOLD, 50));
        lblNewLabel.setBounds(100, 50, 250, 120);
        add(lblNewLabel);

        JPasswordField password_JPasswordField = new JPasswordField(20);
        password_JPasswordField.setFont(new Font("宋体", Font.BOLD, 20));
        password_JPasswordField.setEchoChar('*');
        password_JPasswordField.setBounds(100, 300, 250, 40);
        password_JPasswordField.setEditable(true);
        add(password_JPasswordField);

        ImageIcon image2 = new ImageIcon(frame.imagePath+ "解锁.png");
        JButton btnNewButton = new JButton("解锁",image2);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(password_JPasswordField.getText(), "")){
                    return;
                }
                Login a = new Login();
                a.isLocked = true;
                a.setPhone(frame.member.getPhone());
                a.setPassword(password_JPasswordField.getText());
                try {
                    frame.client.sendObject(a);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                password_JPasswordField.setText("");
            }
        });
        btnNewButton.setFont(new Font("宋体标题", Font.BOLD, 25));
        btnNewButton.setForeground(Color.BLUE);
        btnNewButton.setBounds(150, 360, 150, 40);
        btnNewButton.setContentAreaFilled(false);
        add(btnNewButton);
    }
}
