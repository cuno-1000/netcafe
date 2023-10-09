package com.netcafe.common.param;

import com.netcafe.common.data.Members;

import java.io.Serializable;

public class Password extends Members implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;
    String oldPassword;
    String newPassword;

    public Password(int memberID, String phone,String oldPassword, String newPassword) {
        super(memberID, phone, "","","","");
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }


}
