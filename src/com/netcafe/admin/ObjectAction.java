package com.netcafe.admin;

import com.google.gson.Gson;
import com.netcafe.admin.db.Select;
import com.netcafe.admin.db.Updata;
import com.netcafe.common.data.GreetParams;
import com.netcafe.common.data.Members;
import com.netcafe.common.param.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public interface ObjectAction {
    Object doAction(Object rev);
}

final class DefaultObjectAction implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        return "ka:::ok";
    }
}

final class HelloAction implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        GreetParams a = (GreetParams) rev;
        String ret = "你好" + a.getId() + "先生," + a.getAge() + "歲生日快樂！";
        System.out.println("处理并返回：" + ret);
        return ret;
    }
}

final class LoginAction implements ObjectAction {
    Login a;
    @Override
    public Object doAction(Object rev) {
        a = (Login) rev;
        Select select = new Select();

        // 邓竣中 下同
        Object[][] result = select.getMembers("SELECT memberid,phone,name,sex,joinDate,balance FROM members WHERE phone = '" + a.getPhone() + "' AND password = '" + a.getPassword() + "';");
        if (result.length == 0 || (int)result[0][0]==0) {
            return "login:::ERROR";
        }
        if (Float.parseFloat(String.valueOf(result[0][5])) < 15) {
            return "login:::BALANCE";
        }

        if(!a.isLocked){
            a.setMemberID((int)result[0][0]);
            broadcastUsableMachines();

            Updata updata = new Updata();
            updata.addData("INSERT INTO sessions (memberID, onLineAt, ipAddressPort) VALUES (" + result[0][0] + ", NOW(), '" + a.socket.getInetAddress() + ":" + a.socket.getPort() + "');");
        }

        Consume b = new Consume((int)result[0][0]);
        b.setResult(select.getUserPageBills("select ipAddressPort, onLineAt, deletedAt from sessions WHERE memberID = '"+ result[0][0] +"' ORDER BY sessionID DESC"));

        Members member = new Members((int)result[0][0],(String)result[0][1],(String)result[0][2],
                (String)result[0][3],(String)result[0][4],(String)result[0][5]);

        return "login:::" + new Gson().toJson(member) + ":::" + new Gson().toJson(b);
    }
    void broadcastUsableMachines() {
        a.usedMachines.put(a.socket.getInetAddress()+":"+a.socket.getPort(),a.getMemberID());
        try {
            Server.broadcastUsableMachines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

final class SetUpAction implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        SetUp a = (SetUp) rev;
        Updata updata = new Updata();
        int result = updata.addData("INSERT INTO sessions (phone,name,sex,joinDate,balance,password) VALUES ('"+a.getPhone()+"','"+a.getName()+"','"+a.getSex()+"',NOW(),'"+a.getBalance()+"','"+a.getPassword()+"',);");
        if (result == -1) {
            return "logout:::ERROR";
        }
        return "logout:::OK";
    }
}

final class ChangeAction implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        Password a = (Password) rev;

        Updata updata = new Updata();
        int result = updata.addData("UPDATE members SET password = "+a.getNewPassword()+" WHERE memberID='" + a.getMemberID() + "' AND  phone = '" + a.getPhone() + "' AND password = '" + a.getOldPassword() + "';");
        if (result == 0) {
            return "change-password:::ERROR";
        }
        return "change-password:::OK";
    }
}

final class LogOutAction implements ObjectAction {
    Logout a;
    @Override
    public Object doAction(Object rev) {
        a = (Logout) rev;
        Updata updata = new Updata();

        int result = updata.addData("UPDATE sessions SET deletedAt = NOW() WHERE deletedAt IS NULL AND memberID='" + a.getMemberID()+ "';");
        broadcastUsableMachines();

        if (result == -1) {
            return "logout:::ERROR";
        }
        return "logout:::OK";
    }
    void broadcastUsableMachines() {
        a.usedMachines.remove(a.socket.getInetAddress() + ":" + a.socket.getPort(), a.getMemberID());
        try {
            Server.broadcastUsableMachines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

final class CheckAction implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        Balance a = (Balance) rev;
        Select select = new Select();

        Object[][] result = select.getMembers("SELECT memberid,phone,name,sex,joinDate,balance FROM members WHERE memberID = '" + a.getMemberID() + "';");

        float b = Float.parseFloat(String.valueOf(result[0][5])) - 15;
        if (b < 0) {
            return "check:::ERROR";
        }
        Updata updata = new Updata();
        if (a.isDeducting()) {
            updata.addData("UPDATE members SET balance = " + b + " WHERE memberID = '" + a.getMemberID() + "';");
        }
        return "check:::OK";
    }
}

final class FetchRecord implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        Consume a = (Consume) rev;
        Select select = new Select();

        if(!a.getColumnName().equals("") && !a.getKeyWord().equals("")){
            a.setResult(select.getConsumes("SELECT * FROM sessions WHERE memberID = '"+ a.getMemberID() +"' AND "+ a.getColumnName() + " LIKE \"%" + a.getKeyWord() + "%\" ORDER BY deletedAt DESC;"));
            return "consume-record:::OK:::" + new Gson().toJson(a);
        }
        a.setResult(select.getConsumes("SELECT * FROM sessions WHERE memberID = '"+ a.getMemberID() +"' ORDER BY sessionID DESC;"));
        return "consume-record:::OK:::" + new Gson().toJson(a);
    }
}

final class FetchUsableMachines implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        UsableMachines a = (UsableMachines) rev;
        Set<String> complementKeys = a.clientSockets.keySet().stream()
                .filter(key -> !a.usedMachines.containsKey(key))
                .collect(Collectors.toSet());
        String usableMachinesList = new Gson().toJson(complementKeys);
        return "update-machine-list:::"+usableMachinesList;
    }
}

final class MigrateAction implements ObjectAction {
    Migrate a;
    @Override
    public Object doAction(Object rev) {
        a = (Migrate) rev;
        Select select = new Select();

        Object[][] result = select.getMembers("SELECT memberid,phone,name,sex,joinDate,balance FROM members WHERE memberID = '" + a.getMemberID() + "';");
        if (result.length == 0) {
            return "migrate:::ERROR";
        }

        Updata updata = new Updata();
        updata.addData("UPDATE sessions SET deletedAt = NOW() WHERE deletedAt IS NULL AND memberID='" + a.getMemberID()+ "';");
        updata.addData("INSERT INTO sessions (memberID, onLineAt, ipAddressPort) VALUES (" + result[0][0] + ", NOW(), '" + a.socket.getInetAddress() + ":" + a.socket.getPort() + "');");

        Consume b = new Consume((int)result[0][0]);
        b.setResult(select.getUserPageBills("select ipAddressPort, onLineAt, deletedAt from sessions WHERE memberID = '"+ result[0][0] +"' ORDER BY sessionID DESC"));
        Members member = new Members((int)result[0][0],(String)result[0][1],(String)result[0][2],
                (String)result[0][3],(String)result[0][4],(String)result[0][5]);
        String resp =  "login:::" + new Gson().toJson(member) + ":::" + new Gson().toJson(b);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    a.clientSockets.get(a.getAddress()).getOutputStream());
            oos.writeObject(resp);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateUsableMachines();
        return "logout:::OK";
    }
    void updateUsableMachines() {
        a.usedMachines.remove(a.socket.getInetAddress()+":"+a.socket.getPort());
        a.usedMachines.put(a.getAddress(),a.getMemberID());
    }
}

final class QueueCalling implements ObjectAction {
    @Override
    public Object doAction(Object rev) {
        Call a = (Call) rev;
        a.callingQueue.offer(a.content);
        return "call:::OK";
    }
}
