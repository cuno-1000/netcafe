package com.netcafe.user.page;

import com.netcafe.common.component.MyTableModel;
import com.netcafe.common.data.Members;
import com.netcafe.common.param.Logout;
import com.netcafe.user.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class UserWindow extends JFrame {

    public final JPanel panel;
    public Client client = null;
    public CardLayout cardLayout = new CardLayout();
    String imagePath = "src/com/netcafe/images/user/";
    public boolean isLocked = false;

    Members member = null;
    public Members getMember() {
        return member;
    }

    public WarningWindow warningWindow = new WarningWindow();

    // 消费记录Table
    public String[] header = {"机器信息", "上线时间", "下线时间", "消费(元)"};
    public MyTableModel consumeDf = null;

    // 可用主机ComboBox
    public JComboBox<String> usableMachinesComboBox = null;

    /**
     * Create the frame.
     */
    public UserWindow() {
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 0);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setSize(600, 500);

        panel = new JPanel(); // 刘德文 // 客户端:全页面控制的单窗口多页面跳转
        panel.setBounds(60, 10, 600, 500);
        contentPane.add(panel);
//		给主要显示面板添加布局方式
        panel.setLayout(cardLayout);
//		创建相应面板类的对象
        LoginPage p1 = new LoginPage(this);
//		将面板添加到住面板中，注意:add()方法里有两个参数，第一个是要添加的对象，第二个给这个对象所放置的卡片
//		起个名字，后面调用显示的时候要用到这个名字
        panel.add(p1, "p1");

        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                // 挂机时不能在客户端下线
                if(isLocked) { // 郑春龄
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }else{
                    if(member!=null){
                        Logout a = new Logout(member.getMemberID());
                        try {
                            client.sendObject(a);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            final String serverIp = "127.0.0.1";
            final int port = 65431;

            public void run() {
                try {
                    UserWindow frame = new UserWindow();
                    frame.client = new Client(serverIp, port);
                    frame.client.frame = frame;
                    frame.client.start();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}