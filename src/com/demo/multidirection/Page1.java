package com.demo.multidirection;

import javax.swing.*;
import java.awt.*;

public class Page1 extends JPanel {
    private final JTextField jt1;

    /**
     * Create the panel.
     */
    public Page1() {
        setLayout(null);

        JLabel lblNewLabel = new JLabel("我是页面1  (*^-^)");
        lblNewLabel.setFont(new Font("宋体", Font.BOLD, 22));
        lblNewLabel.setBounds(34, 26, 233, 69);
        add(lblNewLabel);

        jt1 = new JTextField();
        jt1.setBounds(34, 105, 100, 21);
        add(jt1);
        jt1.setColumns(10);

        JButton btnNewButton = new JButton("提交");

        btnNewButton.setBounds(146, 104, 97, 23);
        add(btnNewButton);

    }
}