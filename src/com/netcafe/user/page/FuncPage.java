package com.netcafe.user.page;

import com.netcafe.common.data.Members;
import com.netcafe.common.param.Consume;
import com.netcafe.common.param.Migrate;
import com.netcafe.common.param.UsableMachines;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class FuncPage extends JPanel {
    UserWindow frame;

    /**
     * Create the panel.
     */
    public FuncPage(Members member, UserWindow a) {
        frame = a;
        frame.member = member;
//        frame.balanceLabel = new JLabel("￥ " + frame.member.getBalance());

        LockedPage p2 = new LockedPage(frame);
        frame.panel.add(p2, "p2");
        PasswordPage p4 = new PasswordPage(frame);
        frame.panel.add(p4, "p4");
        RecordPage p5 = new RecordPage(frame);
        frame.panel.add(p5, "p5");

        setLayout(null);
        String greeting = member.getName().split("")[0]+(member.isMale()?"先生":"小姐");
        JLabel lblNewLabel = new JLabel(greeting+"，欢迎使用");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
        lblNewLabel.setBounds(0, 20, 250, 50);
        add(lblNewLabel);

        JLabel balanceLabel = new JLabel("￥ " + frame.member.getBalance());
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        balanceLabel.setBounds(0, 60, 250, 50);
        add(balanceLabel);

//        JTextField jt2 = new JTextField();
//        jt2.setBounds(42, 114, 206, 21);
//        add(jt2);
//        jt2.setColumns(10);
        if(frame.usableMachinesComboBox==null){
            frame.usableMachinesComboBox = new JComboBox<String>();
        }
        frame.usableMachinesComboBox.setBounds(0, 100, 256, 45);
        add(frame.usableMachinesComboBox);

        // 换机Button
        ImageIcon image1 = new ImageIcon(frame.imagePath+ "换机.png");
        JButton btnNewButton = new JButton("换机",image1);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(frame.usableMachinesComboBox.getSelectedIndex()<0)
                    return;
                Migrate a = new Migrate((String) frame.usableMachinesComboBox.getSelectedItem());
                a.setMemberID(frame.member.getMemberID());
                try {
                    frame.client.sendObject(a);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("宋体标题", Font.BOLD, 20));
        btnNewButton.setForeground(Color.BLUE);
        btnNewButton.setBounds(0, 150, 120, 40);
        btnNewButton.setContentAreaFilled(false);
        add(btnNewButton);

        // 挂机Button
        ImageIcon image2 = new ImageIcon(frame.imagePath+ "挂机.png");
        ImageIcon newIcon = new ImageIcon(
                image2.getImage().getScaledInstance(36, 36, java.awt.Image.SCALE_SMOOTH));
        JButton lockUpButton = new JButton("挂机",newIcon);
        lockUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.isLocked = true;
                frame.cardLayout.show(frame.panel, "p2");
            }
        });
        lockUpButton.setFont(new Font("宋体标题", Font.BOLD, 20));
        lockUpButton.setForeground(Color.BLUE);
        lockUpButton.setBounds(140, 150, 120, 40);
        lockUpButton.setContentAreaFilled(false);
        add(lockUpButton);

        // 修改密码Button
        ImageIcon image3 = new ImageIcon(frame.imagePath+ "密码.png");
        JButton toPwdPageButton = new JButton("修改密码",image3);
        toPwdPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.cardLayout.show(frame.panel, "p4");
            }
        });
        toPwdPageButton.setFont(new Font("宋体标题", Font.BOLD, 20));
        toPwdPageButton.setForeground(Color.BLUE);
        toPwdPageButton.setBounds(0, 205, 260, 40);
        toPwdPageButton.setContentAreaFilled(false);
        add(toPwdPageButton);


//        JTextField oldPasswordField = new JTextField(20);
//        oldPasswordField.setBounds(280, 150, 220, 40);
//        oldPasswordField.setFont(new Font("宋体", Font.BOLD, 25));
//        oldPasswordField.setForeground(Color.BLACK);
//        oldPasswordField.setEditable(true);
//        add(oldPasswordField);
//        ImageIcon image4 = new ImageIcon(frame.imagePath+ "呼叫.png");
//        JButton sendButton = new JButton("呼叫",image4);
//        sendButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//        sendButton.setFont(new Font("宋体标题", Font.BOLD, 20));
//        sendButton.setForeground(Color.BLUE);
//        sendButton.setBounds(280, 205, 220, 40);
//        sendButton.setContentAreaFilled(false);
//        add(sendButton);

        // 消费记录Table
        while(frame.consumeDf==null);
//        if(frame.consumeDf==null){
//            frame.consumeDf = new MyTableModel(new Object[][]{},frame.header);
//        }
        JTable jTable = new JTable(frame.consumeDf);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(30);
        JScrollPane jsp = new JScrollPane(jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jTable.setEnabled(false);
        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.cardLayout.show(frame.panel, "p5");
            }
        });
        jsp.setBounds(0, 280, 500, 120);
        add(jsp);
    }
}
