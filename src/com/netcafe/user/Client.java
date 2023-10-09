package com.netcafe.user;

import com.netcafe.common.component.MyTableModel;
import com.netcafe.common.data.GreetParams;
import com.netcafe.common.data.KeepAlive;
import com.netcafe.common.data.Members;
import com.netcafe.common.param.Balance;
import com.netcafe.common.param.Consume;
import com.netcafe.common.param.Logout;
import com.netcafe.user.page.FuncPage;
import com.netcafe.user.page.UserWindow;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;


/**
 * C/S架构的客户端对象，持有该对象，可以随时向服务端发送消息。
 *
 * @author zyz, Kuanho
 * @version 3.0
 * @date 2023/5/11 21:06
 * @Description:
 */
public class Client {
    public UserWindow frame;
    private final String serverIp;
    private final int port;
    private Socket socket;
    //连接状态
    private boolean running = false;
    /**
     * 最后一次发送数据的时间
     */
    private long lastSendTime;

    /**
     * 用于保存接收消息对象类型及该类型消息处理的对象
     */
    private final ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class, ObjectAction>();

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void start() throws IOException {
        if (running) {
            return;
        }
        socket = new Socket(serverIp, port);
        System.out.println("本地端口：" + socket.getLocalPort());
        lastSendTime = System.currentTimeMillis();
        running = true;

        GreetParams a = new GreetParams();
        a.setAge(18);
        a.setId("楊軍豪");
        Client.this.sendObject(a);

        //保持长连接的线程，每隔2秒项服务器发一个一个保持连接的心跳消息
        new Thread(new KeepAliveWatchDog()).start();
        //接受消息的线程，处理消息
        new Thread(new ReceiveWatchDog()).start();
    }

    public void stop() {
        if (running) {
            running = false;
        }
    }

    public void sendObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(obj);
        oos.flush();
    }

    class KeepAliveWatchDog implements Runnable {
        long checkDelay = 10;
        //两秒钟检测一次
        long keepAliveDelay = 2000;

        @Override
        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        Client.this.sendObject(new KeepAlive());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable {
        java.util.Timer timer1 = new Timer();
        java.util.Timer timer2 = new Timer();
        BlockingQueue<Boolean> queue;
        Gson gson = new Gson();

        @Override
        public void run() {
            while (running) {
                try {
                    InputStream in = socket.getInputStream();
                    if (in.available() > 0) {
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Object obj = ois.readObject();
                        String data = obj.toString();
                        String[] temp = data.split(":::");
                        switch (temp[0]) {
//                            case "ka":
//                                System.out.println("接收：\t" + temp[1]);
//                                break;
                            case "login":

                                if (temp[1].equals("ERROR")) {
                                    if(frame.isLocked && frame.getMember()!=null)
                                        JOptionPane.showMessageDialog(frame, "密码错误，解锁失败");
                                    else JOptionPane.showMessageDialog(frame, "用户不存在或密码错误，登录失败");
                                } else if (temp[1].equals("BALANCE")) {
                                    JOptionPane.showMessageDialog(frame, "用户余额不足，登录失败");
                                } else {
                                    Members member = new Gson().fromJson(temp[1], Members.class);

                                    // 解锁 // 复用登录逻辑
                                    if(frame.isLocked && frame.getMember()!=null){ // 郑春龄
                                        if(frame.getMember().getMemberID()==member.getMemberID()
                                                && frame.getMember().getPhone().equals(member.getPhone())) {
                                            frame.isLocked = false;
                                            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                                            frame.cardLayout.show(frame.panel, "p3");
                                        }
                                    } else {
                                        frame.warningWindow.setLocation(frame.getLocation()); // For Performing

                                        Consume c = new Gson().fromJson(temp[2], Consume.class);
                                        frame.consumeDf = new MyTableModel(c.getResult(), frame.header);

                                        FuncPage p3 = new FuncPage(member, frame);
                                        frame.panel.add(p3, "p3");
                                        frame.cardLayout.show(frame.panel, "p3");

                                        // 计费
                                        queue = new ArrayBlockingQueue<>(1);

                                        timer1.schedule(new TimerTask() { // 郑春龄
                                            final int id = member.getMemberID();
                                            final Balance a = new Balance(id);
                                            public boolean isSufficient(Object a) {
                                                boolean b = false;
                                                try {
                                                    Client.this.sendObject(a);
                                                    b = queue.take();
                                                } catch (IOException | InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                return b;
                                            }

                                            @Override
                                            public void run() {
                                                a.setDeducting(true);// 扣减余额
                                                if (!isSufficient(a)) {
//                                                    System.out.println("不夠");
                                                    TimerTask task2 = new TimerTask() {
                                                        int count = 4;
                                                        @Override
                                                        public void run() {
                                                            if(count==0){
//                                                                System.out.println("不夠,下線"+count);
                                                                Logout c = new Logout(id);
                                                                try {
                                                                    Client.this.sendObject(c);
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                return;
                                                            }
                                                            a.setDeducting(false);
                                                            if (!isSufficient(a)) {
//                                                                System.out.println("不夠,提醒" + count);
                                                                SwingUtilities.invokeLater(() -> {
                                                                    frame.warningWindow.setLabel("请" + (count+1) + "分钟内到前台充值");
                                                                    frame.warningWindow.setVisible(true);
                                                                });
//                                                                frame.warningWindow.setLabel("请" + (count+1) + "分钟内到前台充值");
//                                                                frame.warningWindow.setVisible(true);
                                                            } else {
                                                                System.out.println("夠");
                                                                this.cancel();
                                                                SwingUtilities.invokeLater(() -> {
                                                                    frame.warningWindow.setVisible(false);
                                                                });
//                                                                frame.dialog.setVisible(false);
                                                            }
                                                            count--;
                                                        }
                                                    };
                                                    timer2.schedule(task2,0,2000); // delay: 5 minutes
                                                }
                                                else System.out.println("夠,扣減");
                                            }
                                        }, 0, 10000); // period: 30 minutes
//                                        // 经讨论取缔的半小时计费方案
//                                        timer.schedule(new TimerTask() {
//                                            final int id = member.getMemberID();
//                                            public boolean isSufficient(Object a) {
//                                                try {
//                                                    Client.this.sendObject(a);
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                boolean b = false;
//                                                try {
//                                                    b = queue.take();
//                                                } catch (InterruptedException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                return b;
//                                            }
//
//                                            @Override
//                                            public void run() {
//                                                // 查看是否夠錢，不夠->提醒
//                                                Balance a = new Balance(id);
//                                                if (!isSufficient(a)) {
//                                                    System.out.println("不夠");
////                                                    JOptionPane.showMessageDialog(frame, "請五分鐘內到前台充值");
//                                                } else System.out.println("夠");
//
//                                                timer.schedule(new TimerTask() {
//                                                    @Override
//                                                    public void run() {
//                                                        // 五分鐘後查看，夠錢->扣錢/不夠->強制下線
//                                                        Balance a = new Balance(id);
//                                                        a.setDeducting(true);// 扣減餘額
//                                                        if (!isSufficient(a)) {
//                                                            System.out.println("不夠,下線");
//                                                            Logout c = new Logout(id);
//                                                            try {
//                                                                Client.this.sendObject(c);
//                                                            } catch (IOException e) {
//                                                                e.printStackTrace();
//                                                            }
//                                                        } else System.out.println("夠,扣減");
//                                                    }
//                                                }, 3000); // delay: 5 minutes
//                                            }
//                                        }, 0, 10000); // period: 30 minutes
                                    }
                                }
                                break;
                            case "change-password":
                                if (temp[1].equals("OK")) {
                                    frame.cardLayout.show(frame.panel, "p3");
                                    JOptionPane.showMessageDialog(frame, "密码重设成功");
                                    break;
                                }
                                JOptionPane.showMessageDialog(frame, "密码重设失败");
                                break;
                            case "logout":
                                if (temp[1].equals("OK")) {
                                    System.out.println("logout success");
                                    Client.this.stop();
                                    timer2.cancel();
                                    timer1.cancel();
                                    frame.dispose();
                                    frame.warningWindow.dispose();
                                }else{
                                    JOptionPane.showMessageDialog(frame, "logout failed");
                                    System.out.println("logout failed");
                                }
                                break;
                            case "check":
                                queue.put(temp[1].equals("OK"));
                                break;
                            case "consume-record":
                                System.out.println(Arrays.toString(temp));
                                if (temp[1].equals("OK")) {
                                    Consume c = gson.fromJson(temp[2], Consume.class);
                                    frame.consumeDf.setDataVector(c.getResult());
                                    JOptionPane.showMessageDialog(frame, "共" + c.getResult().length + "条数据");
                                }
                                break;
                            case "update-machine-list":
                                String[] array = gson.fromJson(temp[1], String[].class);
                                if(frame.usableMachinesComboBox==null){
                                    frame.usableMachinesComboBox = new JComboBox<String>();
                                }
                                frame.usableMachinesComboBox.setVisible(array.length!=0);
                                frame.usableMachinesComboBox.setModel(new DefaultComboBoxModel<String>(array));
                                break;
                            case "migrate":
                                if (temp[1].equals("ERROR")) {
                                    JOptionPane.showMessageDialog(frame, "migrate failed");
                                    System.out.println("migrate failed");
                                }
                                break;
                        }
                        ObjectAction oa = actionMapping.get(obj.getClass());
                        oa = oa == null ? new DefaultObjectAction() : oa;
                        oa.doAction(obj, Client.this);
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }

//    public static void main(String[] args) throws UnknownHostException, IOException {
//        String serverIp = "127.0.0.1";
//        int port = 65432;
//        Client client = new Client(serverIp, port);
//        client.start();
//    }
}