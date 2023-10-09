package com.demo.multidirection;

import javax.swing.*;
import java.awt.*;

public class Page2 extends JPanel {

    /**
     * Create the panel.
     */
    public Page2() {
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

    }
}