package com.demo.tcp.server;

import com.netcafe.common.data.GreetParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * C/S架构的服务端对象。
 *
 * @author Kuanho
 * @version 3.0
 * @data 2023/5/9 21:06
 * @Description:
 */
public class Server {


    private final int port;
    private volatile boolean running = false;
    private final long receiveTimeDelay = 3000;
    /**
     * ConcurrentHashMap是线程安全的，ConcurrentHashMap并非锁住整个方法，
     * 而是通过原子操作和局部加锁的方法保证了多线程的线程安全，且尽可能减少了性能损耗。
     */
    private final ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class, ObjectAction>();
    private Thread connWatchDog;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 65432;
        Server server = new Server(port);
        server.start();
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

        // test
        actionMapping.put(GreetParams.class, new HelloAction());

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

    public void addActionMap(Class<Object> cls, ObjectAction action) {
        actionMapping.put(cls, action);
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
            while (running && run) {
                if (System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
                    overThis();
                } else {
                    try {
                        InputStream in = s.getInputStream();
                        if (in.available() > 0) {
                            ObjectInputStream ois = new ObjectInputStream(in);
                            Object obj = ois.readObject();
                            lastReceiveTime = System.currentTimeMillis();
                            System.out.println("接收：\t" + obj);
                            ObjectAction oa = actionMapping.get(obj.getClass());
                            oa = oa == null ? new DefaultObjectAction() : oa;
                            Object out = oa.doAction(obj, Server.this);
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
            }
            System.out.println("关闭：" + s.getRemoteSocketAddress());
        }

    }

}