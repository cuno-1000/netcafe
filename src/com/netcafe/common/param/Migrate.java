package com.netcafe.common.param;

import java.io.Serializable;
import java.net.Socket;

public class Migrate extends Login implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    public Migrate(String address) {
        this.address = address;
    }

    // with Port
    String address;
    public String getAddress() {
        return address;
    }
}
