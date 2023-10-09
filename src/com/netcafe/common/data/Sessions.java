package com.netcafe.common.data;

import java.io.Serializable;

public class Sessions implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;
    int sessionID;
    int memberID;
    String onLineAt;
    String ipAddressPort;
    String deleteAt;

    public Sessions() {
        super();
    }

    public Sessions(int sessionID, int memberID, String onLineAt, String ipAddressPort, String deleteAt) {
        super();
        this.sessionID = sessionID;
        this.memberID = memberID;
        this.onLineAt = onLineAt;
        this.ipAddressPort = ipAddressPort;
        this.deleteAt = deleteAt;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getOnLineAt() {
        return onLineAt;
    }

    public void setOnLineAt(String onLineAt) {
        this.onLineAt = onLineAt;
    }

    public String getIpAddressPort() {
        return ipAddressPort;
    }

    public void setIpAddressPort(String ipAddressPort) {
        this.ipAddressPort = ipAddressPort;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    // complementary
    String phone;
    String name;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}