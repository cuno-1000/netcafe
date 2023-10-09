package com.demo.onlydirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P1 extends JPanel {
    Jrame2 j2;

    private final JTextField jt1;

    /**
     * Create the panel.
     */
    public P1(Jrame2 a) {
        j2 = a;

        JLabel lblNewLabel = new JLabel("我是页面1  (*^-^)");
        lblNewLabel.setFont(new Font("宋体", Font.BOLD, 22));
        lblNewLabel.setBounds(34, 26, 233, 69);
        add(lblNewLabel);

        jt1 = new JTextField();
        jt1.setBounds(34, 105, 100, 21);
        add(jt1);
        jt1.setColumns(10);

        JButton btnNewButton = new JButton("提交");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                j2.cardLayout.show(j2.panel, "p2");
            }
        });

        btnNewButton.setBounds(146, 104, 97, 23);
        add(btnNewButton);

    }
}
