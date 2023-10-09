package com.netcafe.common.param;

import java.io.Serializable;
import java.net.Socket;

public class Logout extends UsableMachines implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    int memberID;

    public Logout(int memberID) {
        this.memberID = memberID;
    }

    public int getMemberID() {
        return memberID;
    }
}
