package com.netcafe.common.data;

import java.io.Serializable;

public class Bills implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;
    int billID;
    int memberID;
    String time;
    String amount;

    public Bills() {
        super();
    }

    public Bills(int billID, int memberID, String time, String amount) {
        this.billID = billID;
        this.memberID = memberID;
        this.time = time;
        this.amount = amount;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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