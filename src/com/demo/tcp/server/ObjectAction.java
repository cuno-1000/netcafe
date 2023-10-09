package com.demo.tcp.server;

import com.netcafe.admin.db.Select;
import com.netcafe.common.data.GreetParams;
import com.netcafe.common.data.Members;

public interface ObjectAction {
    Object doAction(Object rev, Server server);
}

final class DefaultObjectAction implements ObjectAction {
    @Override
    public Object doAction(Object rev, Server server) {
        System.out.println("处理并返回：" + rev);
        return rev;
    }
}

final class HelloAction implements ObjectAction {
    @Override
    public Object doAction(Object rev, Server server) {
        GreetParams a = (GreetParams) rev;
        String ret = "你好" + a.getId() + "先生," + a.getAge() + "歲生日快樂！";
        System.out.println("处理并返回：" + ret);
        return ret;
    }
}

final class LoginAction implements ObjectAction {
    @Override
    public Object doAction(Object rev, Server server) {
        Members a = (Members) rev;
        Select select = new Select();

        Object[][] result = select.getMembers("login:::SELECT memberid,phone,name,sex,joinDate,balance FROM members WHERE phone = '" + a.getPhone() + "' AND password = '" + a.getJoinDate() + "';");
//        if (result.length > 0) {
//            Updata updata = new Updata();
//            oos.writeObject("login:::"+result[0][0].toString());
////                                        updata.addData("INSERT INTO sessions SELECT * FROM members WHERE memberID = " + result[0][0] + ";");
//            updata.addData("INSERT INTO sessions (memberID, onLineAt, ipAddressPort) VALUES ("+result[0][0]+", NOW(), '"+s.getInetAddress()+":"+s.getPort()+"');");
//        } else {
//            oos.writeObject("login:::ERROR");
//        }
//        oos.flush();
//
//
//        System.out.println("处理并返回：" + ret);
        return result;
    }
}