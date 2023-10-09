package com.demo.onlydirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P2 extends JPanel {
    Jrame2 j2;

    /**
     * Create the panel.
     */
    public P2(Jrame2 a) {
        j2 = a;

        setLayout(null);
        JLabel lblNewLabel = new JLabel("我是页面2  (*^▽^*)");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        lblNewLabel.setBounds(23, 10, 245, 94);
        add(lblNewLabel);

        JTextField jt2 = new JTextField();
        jt2.setBounds(42, 114, 206, 21);
        add(jt2);
        jt2.setColumns(10);

        JButton btnNewButton = new JButton("确定");
        btnNewButton.setBounds(42, 145, 97, 23);
        add(btnNewButton);

        JButton btnNewButton2 = new JButton("返回");
        btnNewButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                j2.cardLayout.show(j2.panel, "p1");
            }
        });

        btnNewButton2.setBounds(146, 145, 97, 23);
        add(btnNewButton2);

    }
}
