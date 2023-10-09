package com.netcafe.common.data;

import java.io.Serializable;
import java.util.Objects;

public class Members implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;
    int memberID;
    String phone;
    String name;
    String sex;
    String joinDate;
    String balance;

    public Members() {
        super();
    }

    public Members(int memberID, String phone, String name, String sex, String joinDate, String balance) {
        super();
        this.memberID = memberID;
        this.phone = phone;
        this.name = name;
        this.sex = sex;
        this.joinDate = joinDate;
        this.balance = balance;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean isMale() {
        return Objects.equals(sex, "男");
    }
}