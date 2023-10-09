package com.netcafe.common.param;

import com.netcafe.common.data.Members;

import java.io.Serializable;

public class Balance extends Members implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;
    boolean isDeducting = false;

    public Balance(int memberId) {
        setMemberID(memberId);
    }

    public boolean isDeducting() {
        return isDeducting;
    }

    public void setDeducting(boolean deducting) {
        isDeducting = deducting;
    }

}
