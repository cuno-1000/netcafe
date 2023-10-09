package com.netcafe.admin.page;

import com.netcafe.admin.db.Select;
import com.netcafe.common.component.MyTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class IncomePage extends JPanel {

    Select select = new Select();
    String[] headerDb = {"phone", "name", "time", "amount"};
    String[] header = {"手机", "姓名", "时间", "金额"};
    String sql = "select phone, name, time, amount " +
        "from members right join bills " +
        "on members.memberID = bills.memberID;"; // 邓竣中
    Object[][] data = select.getBills(sql);
    MyTableModel df = new MyTableModel(data, header);

    //constructor
    IncomePage() {
        setLayout(null);

        JTextField keywordInput = new JTextField(16);
        keywordInput.setBounds(7, 13, 200, 23);
        add(keywordInput);

        final JComboBox<String> comboBox = new JComboBox<String>(header);
        comboBox.setBounds(210, 13, 93, 24);
        add(comboBox);

        JTable jTable = new JTable(df);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        JScrollPane jsp = new JScrollPane(jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(10, 47, 580, 277);
        add(jsp);

        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(new ActionListener() { // 方濮徽
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = keywordInput.getText();
                Object[][] data;
                String sql;
                if (Objects.equals(input, "")) {
//                    sql = "SELECT * FROM bills;";
                    sql = "select phone, name, time, amount " +
                            "from members right join bills " +
                            "on members.memberID = bills.memberID;";
                } else {
                    int index = comboBox.getSelectedIndex();
                    sql = "select phone, name, time, amount " +
                            "from members right join bills " +
                            "on members.memberID = bills.memberID " +
//                    sql = "SELECT * FROM bills;" +
                            "WHERE " + headerDb[index] + " LIKE \"%" + input + "%\";";
                }
                data = select.getBills(sql);
                df.setDataVector(data);
                JOptionPane.showMessageDialog(null, "共" + data.length + "条数据");
            }
        });
        searchBtn.setBounds(303, 13, 93, 24);
        add(searchBtn);
    }
}