package com.netcafe.admin.page;

import com.netcafe.admin.db.Select;
import com.netcafe.common.component.MyTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ConsumePage extends JPanel {
    Select select = new Select();
    String[] headerDb = {"phone", "ipAddressPort", "onLineAt", "deletedAt"};
    String[] header = {"手机", "机器信息", "上线时间", "下线时间"};
    String sql = "select phone, ipAddressPort, onLineAt, deletedAt " +
            "from members right join sessions " +
            "on members.memberID = sessions.memberID WHERE deletedAt IS NOT NULL;"; // 邓竣中
    Object[][] data = select.getConsumes(sql);
    MyTableModel df = new MyTableModel(data, header);

    ConsumePage() {
        setLayout(null);

        JTextField keywordInput = new JTextField(16);
        keywordInput.setBounds(7, 13, 200, 23);
        add(keywordInput);

        final JComboBox<String> comboBox = new JComboBox<String>(header);
        comboBox.setBounds(210, 13, 93, 24);
        add(comboBox);

        JTable jTable = new JTable(df);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(100);
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
                if (Objects.equals(input, "")) {
                    String sql = "select phone, ipAddressPort, onLineAt, deletedAt " +
                            "from members right join sessions " +
                            "on members.memberID = sessions.memberID WHERE deletedAt IS NOT NULL;";
                    data = select.getMembers(sql);
                } else {
                    int index = comboBox.getSelectedIndex();
                    String sql = "select phone, ipAddressPort, onLineAt, deletedAt " +
                            "from members right join sessions " +
                            "on members.memberID = sessions.memberID " +
                            "WHERE " + headerDb[index] + " LIKE \"%" + input + "%\";";
                    data = select.getConsumes(sql);
                }
                df.setDataVector(data);
                JOptionPane.showMessageDialog(null, "共" + data.length + "条数据");
            }
        });
        searchBtn.setBounds(303, 13, 93, 24);
        add(searchBtn);
    }
}
