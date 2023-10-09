package com.netcafe.admin.page;

import com.netcafe.admin.db.Select;
import com.netcafe.admin.db.Updata;
import com.netcafe.common.component.MyTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class OnlinePage extends JPanel {

    AdminWindow frame;
    Select select = new Select();
    Updata updata = new Updata();
    String[] header = {"序号", "手机", "姓名", "上线时间", "机器信息"};
    String sql = "select members.memberID, phone, name, onLineAt, ipAddressPort " +
            "from members right join sessions " +
            "on members.memberID = sessions.memberID WHERE deletedAt IS NULL;"; // 邓竣中
    Object[][] data = select.getOnLines(sql);
    MyTableModel df = new MyTableModel(data, header);

    //constructor
    OnlinePage(AdminWindow a) {
        frame=a;
        setLayout(null);

        JTable jTable = new JTable(df);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(60);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(100);
        JScrollPane jsp = new JScrollPane(jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(10, 47, 580, 277);
        add(jsp);

        JButton logOutBtn = new JButton("下线");
        logOutBtn.addActionListener(new ActionListener() { // 方濮徽
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable.getSelectedColumn() < 0) {
                    JOptionPane.showMessageDialog(null, "请选中需要被强制下线的主机");
                } else {
                    int ok = JOptionPane.showConfirmDialog(null, "您确定要强制下线该主机吗？!", "强制下线提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (ok == JOptionPane.OK_OPTION) {
                        int id = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 0).toString());
                        String address = jTable.getValueAt(jTable.getSelectedRow(), 4).toString();
                        String sql1 = "UPDATE sessions SET deletedAt = NOW() WHERE memberID= "+id;
                        int result1 = updata.addData(sql1);
                        Object[][] data = select.getOnLines(sql);
                        df.setDataVector(data);
                        int result2 = logOutClient(address);
                        if (result1 > 0 && result2 >0) {
                            JOptionPane.showMessageDialog(null, "该会员下线成功！");
                        } else {
                            JOptionPane.showMessageDialog(null, "下线失败！");
                        }
                    }
                }
            }
        });
        logOutBtn.setBounds(5, 13, 589, 24);
        add(logOutBtn);
    }
    public int logOutClient(String address) {
        try {
            Socket socket = frame.server.getClientSockets(address);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("logout:::OK");
            oos.flush();
            return 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}