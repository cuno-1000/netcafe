package com.netcafe.common.param;

import java.io.Serializable;
import java.net.Socket;

public class Login extends UsableMachines implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    String Phone;
    String Password;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    // response
    int memberID;

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public boolean isLocked = false;
}
