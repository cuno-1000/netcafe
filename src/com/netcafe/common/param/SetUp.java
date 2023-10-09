package com.netcafe.common.param;

import com.netcafe.common.data.Members;

import java.io.Serializable;

public class SetUp extends Members implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    public String getPassword() {
        return password;
    }

    String password;

    public SetUp(int memberID, String phone, String name, String sex, String joinDate, String balance, String password) {
        super(memberID, phone, name, sex, joinDate, balance);
        this.password = password;
    }
}
