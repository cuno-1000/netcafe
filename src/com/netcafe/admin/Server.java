package com.netcafe.admin;

import com.google.gson.Gson;
import com.netcafe.admin.page.AdminWindow;
import com.netcafe.common.data.GreetParams;
import com.netcafe.common.param.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * C/S架构的服务端对象。
 *
 * @author zyz, Kuanho
 * @version 3.0
 * @data 2023/5/9 21:06
 * @Description:
 */
public class Server {
    public AdminWindow frame;
    private final int port;
    private volatile boolean running = false;
    /**
     * ConcurrentHashMap是线程安全的，ConcurrentHashMap并非锁住整个方法，
     * 而是通过原子操作和局部加锁的方法保证了多线程的线程安全，且尽可能减少了性能损耗。
     */
    private final ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class, ObjectAction>();
    private Thread connWatchDog;

    static HashMap<String, Socket> clientSockets = new HashMap<String, Socket>();
    public Socket getClientSockets(String ip) {
        if (clientSockets.containsKey(ip)) {
            return clientSockets.get(ip);
        }
        return null;
    }

    static HashMap<String, Integer> usedMachines = new HashMap<String, Integer>();
    static Queue<String> callingQueue = new ArrayDeque<>();

    public Server(int port) {
        this.port = port;
    }

    /**
     * 通过继承Runnable ,开启线程
     */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        connWatchDog = new Thread(new ConnWatchDog());

        actionMapping.put(GreetParams.class, new HelloAction()); // test
        actionMapping.put(Login.class, new LoginAction());
        actionMapping.put(SetUp.class, new SetUpAction());
        actionMapping.put(Logout.class, new LogOutAction());
        actionMapping.put(Balance.class, new CheckAction());
        actionMapping.put(Password.class, new ChangeAction());
        actionMapping.put(Consume.class, new FetchRecord());
        actionMapping.put(Migrate.class, new MigrateAction());
        actionMapping.put(UsableMachines.class, new FetchUsableMachines());
        actionMapping.put(Call.class, new QueueCalling());

        connWatchDog.start();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        if (running) {
            running = false;
        }
        if (connWatchDog != null) {
            connWatchDog.stop();
        }
    }

    /**
     * 继承Runnable 重写run方法、实现线程
     */
    class ConnWatchDog implements Runnable {
        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(port, 5);
                while (running) {
                    Socket s = ss.accept();
                    new Thread(new SocketAction(s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.this.stop();
            }
        }
    }

    /**
     * 继承Runnable 重写run方法、实现线程，通过匿名内部类
     */
    class SocketAction implements Runnable {
        Socket s;
        boolean run = true;
        long lastReceiveTime = System.currentTimeMillis();

        public SocketAction(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            clientSockets.put(s.getInetAddress()+":"+s.getPort(), s);
            try {
                broadcastUsableMachines();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (running && run) {
                long receiveTimeDelay = 3000;
                if (System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
                    overThis();
                } else {
                    try {
                        InputStream in = s.getInputStream();
                        if (in.available() > 0) {
                            ObjectInputStream ois = new ObjectInputStream(in);
                            Object obj = ois.readObject();

                            specifyHandle(obj); // 特殊处理

                            lastReceiveTime = System.currentTimeMillis();
                            ObjectAction oa = actionMapping.get(obj.getClass());
                            oa = oa == null ? new DefaultObjectAction() : oa;
                            Object out = oa.doAction(obj);
                            if (out != null) {
                                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                                oos.writeObject(out);
                                oos.flush();
                            }
                        } else {
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        overThis();
                    }
                }
            }
            usedMachines.remove(s.getInetAddress()+":"+s.getPort());
            clientSockets.remove(s.getInetAddress()+":"+s.getPort());
            try {
                broadcastUsableMachines();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void overThis() {
            if (run) {
                run = false;
            }
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("关闭：" + s.getRemoteSocketAddress());
            }
        }

        private void specifyHandle(Object obj) {
            // 特殊处理
            if (obj.getClass().toString().equals("class com.netcafe.common.param.Login")
                    || obj.getClass().toString().equals("class com.netcafe.common.param.Logout")
                    || obj.getClass().toString().equals("class com.netcafe.common.param.Migrate")) {
                ((UsableMachines) obj).socket = s;
                ((UsableMachines) obj).clientSockets = clientSockets;
                ((UsableMachines) obj).usedMachines = usedMachines;
            }
        }
    }

    public static void broadcastUsableMachines() throws IOException {
        Set<String> complementKeys = clientSockets.keySet().stream()
                .filter(key -> !usedMachines.containsKey(key))
                .collect(Collectors.toSet());
        String usableMachinesList = new Gson().toJson(complementKeys);

        for (String key : usedMachines.keySet()) {
            ObjectOutputStream oos = new ObjectOutputStream(clientSockets.get(key).getOutputStream());
            oos.writeObject("update-machine-list:::"+usableMachinesList);
            oos.flush();
        }
    }

//    public static void main(String[] args) {
//        int port = 65432;
//        Server server = new Server(port);
//        server.start();
//    }
}