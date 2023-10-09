package com.netcafe.admin.page;

import com.netcafe.admin.db.Select;
import com.netcafe.admin.db.Updata;
import com.netcafe.common.component.MyTableModel;
import com.netcafe.common.component.TableCellListener;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.netcafe.common.util.AdultVerification.isValidAge;
import static com.netcafe.common.util.IdCardVerification.isIDCardValid;

public class UserInfoPage extends JPanel {
    Select select = new Select();
    Updata updata = new Updata();
    String[] headerDb = {"memberID", "phone", "name", "sex", "joinDate", "balance"};
    String[] header = {"序号", "手机", "姓名", "性别", "会员日期", "卡余额"};
    String sql = "SELECT memberID,phone,name,sex,joinDate,balance FROM members;"; // 邓竣中
    Object[][] data = select.getMembers(sql);

    String[] setUpParam=new String[6];

    /**
     * Create the panel.
     */
    public UserInfoPage() {
        setLayout(null);

        JTextField keywordInput = new JTextField(16);
        keywordInput.setBounds(7, 13, 200, 23);
        add(keywordInput);

        final JComboBox<String> comboBox = new JComboBox<String>(header);
        comboBox.setBounds(210, 13, 93, 24);
        add(comboBox);

        setUpParam[0]="用户注册：";
        data[data.length-1] = setUpParam;

        MyTableModel df = new MyTableModel(data, header);
        JTable jTable = new JTable(df);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(20);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(70);
        JScrollPane jsp = new JScrollPane(jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        {
            jTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { // 方濮徽 用户注册
//                System.out.println(jTable.rowAtPoint(e.getPoint()));
//                System.out.println(jTable.columnAtPoint(e.getPoint()));
                    int row = jTable.rowAtPoint(e.getPoint());
                    if (row == data.length - 1 && jTable.columnAtPoint(e.getPoint()) == 0
                            && setUpParam[1] != null && isMobileNum(setUpParam[1])
                            && setUpParam[2] != null
                            && setUpParam[3] != null
                            && setUpParam[4] != null && isIDCardValid(setUpParam[4]) && isValidAge(setUpParam[4])
                            && setUpParam[5] != null) {
                        updata.addData("INSERT INTO members (phone,name,sex,joinDate,balance,password) VALUES ('" + setUpParam[1] + "','" + setUpParam[2] + "','" + setUpParam[3] + "',NOW(),'" + setUpParam[5] + "','" + setUpParam[4].substring(12, 18) + "');");
                        setUpParam = new String[6];
                        setUpParam[0] = "用户注册：";
                        String input = keywordInput.getText();
                        if (Objects.equals(input, "")) {
                            String sql2 = "SELECT memberid,phone,name,sex,joinDate,balance FROM members;";
                            data = select.getMembers(sql2);
                        } else {
                            int index = comboBox.getSelectedIndex();
                            String sql = "SELECT * FROM members WHERE " + headerDb[index] + " LIKE \"%" + input + "%\";";
                            data = select.getMembers(sql);
                        }
                        data[data.length - 1] = setUpParam;
                        df.setDataVector(data);
                        jTable.repaint();
                    }
                }
            });
            Action action = new AbstractAction() {
                public void actionPerformed(ActionEvent e) { // 方濮徽
                    // 得到表格触发信息，修改数据
                    TableCellListener tcl = (TableCellListener) e.getSource();
                    if (((TableCellListener) e.getSource()).getTable().getValueAt(tcl.getRow(), 0).equals("用户注册：")) {
                        setUpParam[tcl.getColumn()] = tcl.getNewValue().toString();
                        System.out.println(Arrays.deepToString(setUpParam));
                    } else {
                        if (tcl.getColumn() < 5) {
                            updata.addData("UPDATE members SET " + headerDb[tcl.getColumn()] + "='" + tcl.getNewValue() + "' WHERE memberID = " + ((TableCellListener) e.getSource()).getTable().getValueAt(tcl.getRow(), 0) + ";");
                        } else {
                            if (tcl.getRow() == data.length - 1) return;
                            if (Float.parseFloat(tcl.getOldValue().toString()) > Float.parseFloat(tcl.getNewValue().toString())) {
                                JOptionPane.showMessageDialog(null, "手動扣費操作！");
                            }
                            updata.addData("UPDATE members SET " + headerDb[tcl.getColumn()] + "=" + tcl.getNewValue() + " WHERE memberID = " + ((TableCellListener) e.getSource()).getTable().getValueAt(tcl.getRow(), 0) + ";");
                            updata.addData("INSERT INTO bills (memberID, time, amount) VALUES (" + ((TableCellListener) e.getSource()).getTable().getValueAt(tcl.getRow(), 0) + ", NOW(), " + (Double.parseDouble(tcl.getNewValue().toString()) - Double.parseDouble(tcl.getOldValue().toString())) + ");");
                        }
                    }
                }
            };
            TableCellListener tcl = new TableCellListener(jTable, action);
        }
        jsp.setBounds(10, 47, 580, 277);
        add(jsp);

        JButton searchBtn = new JButton("搜索");
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 方濮徽
                String input = keywordInput.getText();
                if (Objects.equals(input, "")) {
                    String sql2 = "SELECT memberid,phone,name,sex,joinDate,balance FROM members;";
                    data = select.getMembers(sql2);
                } else {
                    int index = comboBox.getSelectedIndex();
                    String sql = "SELECT * FROM members WHERE " + headerDb[index] + " LIKE \"%" + input + "%\";";
                    data = select.getMembers(sql);
                }
                data[data.length-1] = setUpParam;
                df.setDataVector(data);
                JOptionPane.showMessageDialog(null, "共" + (data.length - 1) + "条数据");
            }
        });
        searchBtn.setBounds(303, 13, 93, 24);
        add(searchBtn);

        JButton deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(new ActionListener() { // 方濮徽
            @Override
            public void actionPerformed(ActionEvent e) {
                // 得到表格选中的行，删除数据
                if (jTable.getSelectedColumn() < 0) {
                    JOptionPane.showMessageDialog(null, "请选中要删除的数据！");
                } else {
                    int ok = JOptionPane.showConfirmDialog(null, "您确定要删除该会员信息吗？!", "删除提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (ok == JOptionPane.OK_OPTION) {
                        int id = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 0).toString());
                        String sql = "delete from members where memberid=" + id;
                        int result = updata.addData(sql);
                        String sql2 = "SELECT memberid,phone,name,sex,joinDate,balance FROM members;";
                        data = select.getMembers(sql2);
                        data[data.length-1] = setUpParam;
                        df.setDataVector(data);
                        if (result > 0) {
                            JOptionPane.showMessageDialog(null, "该会员删除成功！");
                        } else {
                            JOptionPane.showMessageDialog(null, "删除失败！");
                        }
                    }
                }
            }
        });
        deleteBtn.setBounds(497, 13, 97, 24);
        add(deleteBtn);
    }

    public static void showSqlErrorWindow(SQLException e) {
        JOptionPane optionPane = new JOptionPane();
        String s;
        if (e.getErrorCode() == 1062) {
            s = "输入的手机号码已存在"+e.getMessage();
        } else {
            s = e.getMessage();
        }
        optionPane.showMessageDialog(null, s);
    }

    public static boolean isMobileNum(String telNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(telNum);
        return m.matches();
    }
}
