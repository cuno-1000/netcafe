package com.demo.tcp.client;

/**
 * 处理服务端发回的对象，可实现该接口。
 */
public interface ObjectAction {
    void doAction(Object obj, Client client);
}

final class DefaultObjectAction implements ObjectAction {
    @Override
    public void doAction(Object obj, Client client) {
        System.out.println("处理：\t" + obj.toString());
    }
}