package com.netcafe.user.page;

import com.netcafe.common.param.Consume;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RecordPage extends JPanel {
    UserWindow frame;

    String[] headerDb = {"memberID", "onLineAt", "deletedAt"};
    String[] header = {"序号", "上线时间", "下线时间"};
    /**
     * Create the panel.
     */
    public RecordPage(UserWindow a) {
        frame = a;

        setLayout(null);
        JLabel lblNewLabel = new JLabel("消费记录页面");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        lblNewLabel.setBounds(23, 10, 245, 94);
        add(lblNewLabel);

        setLayout(null);
        JLabel label = new JLabel("属性");
        final JComboBox<String> comboBox = new JComboBox<String>(header);

        JTextField keywordInput = new JTextField(16);
        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(new ActionListener() { // 方濮徽
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = keywordInput.getText();
                Object[][] data;
                Consume a = new Consume(frame.member.getMemberID());
                int index = comboBox.getSelectedIndex();
                a.setColumnName(headerDb[index]);
                a.setKeyWord(input);
                try {
                    frame.client.sendObject(a);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        keywordInput.setBounds(7, 100, 200, 24);
        comboBox.setBounds(210, 100, 93, 24);
        searchBtn.setBounds(300, 100, 93, 24);
        add(label);
        add(keywordInput);
        add(comboBox);
        add(searchBtn);

        JButton backBtn = new JButton("返回");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.cardLayout.show(frame.panel, "p3");
            }
        });
        backBtn.setBounds(390, 100, 93, 24);
        add(backBtn);

        JTable jTable = new JTable(frame.consumeDf);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(10);
        JScrollPane jsp = new JScrollPane(jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(10, 134, 468, 306);
        add(jsp);
    }
}
